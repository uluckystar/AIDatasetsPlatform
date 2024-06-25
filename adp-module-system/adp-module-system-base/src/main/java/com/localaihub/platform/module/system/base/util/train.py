import subprocess
import sys
import os
import tensorflow as tf

def install(package):
    subprocess.check_call([sys.executable, "-m", "pip", "install", package])

# 安装所需的模块
required_packages = ["tensorflow"]
for package in required_packages:
    try:
        __import__(package)
    except ImportError:
        install(package)

print("Python executable:", sys.executable)
print("Python version:", sys.version)
print("Installed packages:")
os.system("pip freeze")

# 尝试导入不同路径的 preprocessing 模块
try:
    from tensorflow.keras.layers import Rescaling, RandomFlip, RandomRotation
except ImportError:
    from tensorflow.keras.layers.experimental.preprocessing import Rescaling, RandomFlip, RandomRotation

from tensorflow.keras.preprocessing import image_dataset_from_directory
from tensorflow.keras.applications import MobileNetV2
from tensorflow.keras import layers, models

def train_model(dataset_path, model_save_path):
    # 加载数据集
    raw_train_dataset = image_dataset_from_directory(
        dataset_path,
        validation_split=0.2,
        subset="training",
        seed=123,
        image_size=(224, 224),
        batch_size=32
    )

    raw_validation_dataset = image_dataset_from_directory(
        dataset_path,
        validation_split=0.2,
        subset="validation",
        seed=123,
        image_size=(224, 224),
        batch_size=32
    )

    # 获取类别名
    class_names = raw_train_dataset.class_names
    print(f"Class names: {class_names}")

    # 数据预处理
    data_augmentation = tf.keras.Sequential([
        RandomFlip("horizontal_and_vertical"),
        RandomRotation(0.2),
    ])

    normalization_layer = Rescaling(1./255)

    train_dataset = raw_train_dataset.map(lambda x, y: (data_augmentation(x, training=True), y))
    train_dataset = train_dataset.map(lambda x, y: (normalization_layer(x), y))
    train_dataset = train_dataset.cache().prefetch(buffer_size=tf.data.experimental.AUTOTUNE)

    validation_dataset = raw_validation_dataset.map(lambda x, y: (normalization_layer(x), y))
    validation_dataset = validation_dataset.cache().prefetch(buffer_size=tf.data.experimental.AUTOTUNE)

    # 构建模型
    base_model = MobileNetV2(input_shape=(224, 224, 3), include_top=False, weights='imagenet')
    base_model.trainable = False

    model = models.Sequential([
        base_model,
        layers.GlobalAveragePooling2D(),
        layers.Dense(128, activation='relu'),
        layers.Dropout(0.5),
        layers.Dense(len(class_names), activation='softmax')
    ])

    model.compile(optimizer='adam',
                  loss='sparse_categorical_crossentropy',
                  metrics=['accuracy'])

    # 添加回调以记录每个 epoch 的结束
    class CustomCallback(tf.keras.callbacks.Callback):
        def on_epoch_end(self, epoch, logs=None):
            print(f"Epoch {epoch + 1} ended. Accuracy: {logs['accuracy']}, Loss: {logs['loss']}, Val Accuracy: {logs['val_accuracy']}, Val Loss: {logs['val_loss']}")

    # 训练模型
    model.fit(
        train_dataset,
        validation_data=validation_dataset,
        epochs=10,
        callbacks=[CustomCallback()]
    )

    # 保存模型
    model.save(model_save_path)  # 使用指定路径保存模型

if __name__ == "__main__":
    dataset_path = sys.argv[1]
    model_save_path = sys.argv[2]
    train_model(dataset_path, model_save_path)
