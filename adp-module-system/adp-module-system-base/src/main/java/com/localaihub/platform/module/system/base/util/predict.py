import tensorflow as tf
from tensorflow.keras.preprocessing import image
import numpy as np

# 加载模型
model = tf.keras.models.load_model('/Users/jiangjiaxing/project/GraduationProject/AIDatasetsPlatform-test/AIDatasetsPlatform/datasets/宝石识别项目/gem_classification_model.keras')

# 类别名
class_names = ['Alexandrite', 'Almandine', 'Benitoite', 'Beryl Golden', 'Carnelian', 'Cats Eye', 'Danburite', 'Diamond', 'Emerald', 'Fluorite', 'Garnet Red', 'Hessonite', 'Iolite', 'Jade', 'Kunzite', 'Labradorite', 'Malachite', 'Onyx Black', 'Pearl', 'Quartz Beer', 'Rhodochrosite', 'Sapphire Blue', 'Tanzanite', 'Variscite', 'Zircon']  # 替换为你的实际类名

def load_and_preprocess_image(img_path):
    img = image.load_img(img_path, target_size=(224, 224))
    img_array = image.img_to_array(img)
    img_array = tf.expand_dims(img_array, 0)  # 创建batch维度
    img_array = img_array / 255.0  # 归一化
    return img_array

def predict(img_path):
    img_array = load_and_preprocess_image(img_path)
    predictions = model.predict(img_array)
    predicted_class = np.argmax(predictions, axis=1)
    predicted_class_name = class_names[predicted_class[0]]
    return predicted_class_name

if __name__ == "__main__":
    import sys
    img_path = sys.argv[1]
    print(f"Predicted class: {predict(img_path)}")
