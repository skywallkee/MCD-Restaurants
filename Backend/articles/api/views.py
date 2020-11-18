from django.shortcuts import render
from rest_framework import viewsets
from rest_framework.authtoken.models import Token
from rest_framework.response import Response
from rest_framework import status
from rest_framework.decorators import api_view, action
from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth.models import User
from django.contrib.auth import authenticate
from django.http import HttpResponse
from django.http.response import JsonResponse
from rest_framework.parsers import JSONParser
from rest_framework.exceptions import APIException
from django.contrib.auth.hashers import check_password
from django.contrib.auth import authenticate
from .serializers import *
from django.contrib.auth import get_user_model
import json



# class UserViewSet(viewsets.ModelViewSet):
#     serializer_class = UserSerializer
#     # queryset = User.objects.all()


class RestaurantViewSet(viewsets.ModelViewSet):
    serializer_class = RestaurantSerializer
    queryset = Restaurant.objects.all()

    @action(detail=False, methods=['get'],url_path="getRestaurant")
    def get_restaurant(self,request):
        data = request.query_params
        # print("\n\n\n\n\n\n\n\n")
        # print(request.query_params)
        # print("\n\n\n\n\n\n\n\n")
        # print("\n\n\n\n\n\n\n\n")
        # print(data)
        # print("\n\n\n\n\n\n\n\n")
        name=data["name"]
        # restaurants=Restaurant.objects.all().filter(nameR=name)
        restaurant=Restaurant.objects.raw("Select * from articles_restaurant where nameR LIKE '%%"+str(name)+"%%' ORDER BY nameR")
        print(restaurant)
        serializer=RestaurantSerializer(restaurant,many=True)
        # return JsonResponse(serializer.data, safe=False)
        return Response(serializer.data)


class ReviewViewSet(viewsets.ModelViewSet):
    serializer_class = ReviewSerializer
    queryset = Review.objects.all()

class RezervariViewSet(viewsets.ModelViewSet):
    serializer_class = RezervariSerializer
    queryset = Rezervari.objects.all()

class PeretiViewSet(viewsets.ModelViewSet):
    serializer_class = PeretiSerializer
    queryset = Pereti.objects.all()

class MeseViewSet(viewsets.ModelViewSet):
    serializer_class = PeretiSerializer
    queryset = Pereti.objects.all()

@csrf_exempt
@api_view(['POST'])
def createUser(request):
    data = json.loads(request.body.decode('utf-8'))
    if request.method == "POST":
        try:
            if not data["password1"]==data["password2"]:
                raise Exception("The passwords are not the same")
            user=User.objects.create_user(username=data["username"],email=data["email"],password=data["password1"])
            user.save()
            token= Token.objects.create(user=user)
            return Response({'key':token.key})
        except Exception as er:
            print("Error: ",er)
            return Response({'error':str(er)},status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def login(request):
    data = json.loads(request.body.decode('utf-8'))
    if request.method == "POST":
        try:
            user = authenticate(username = data["username"],password = data["password"])
            token= Token.objects.get(user=user)
            return Response({'key':token.key})
        except Exception as er:
            print("Error: ",er)
            return Response({'error':str(er)},status=status.HTTP_400_BAD_REQUEST)