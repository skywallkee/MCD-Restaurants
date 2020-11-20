from rest_framework import serializers
from articles.models import Restaurant,Review,Rezervari,Pereti,Mese


# class UserSerializer(serializers.ModelSerializer):
#     class Meta:
#         model = User
#         fields = '__all__'

class RestaurantSerializer(serializers.ModelSerializer):
    class Meta:
        model = Restaurant
        fields = '__all__'

class ReviewSerializer(serializers.ModelSerializer):
    class Meta:
        model = Review
        fields = '__all__'

class RezervariSerializer(serializers.ModelSerializer):
    class Meta:
        model = Rezervari
        fields = '__all__'

class PeretiSerializer(serializers.ModelSerializer):
    class Meta:
        model = Pereti
        fields = '__all__'

class MeseSerializer(serializers.ModelSerializer):
    class Meta:
        model = Mese
        fields = '__all__'