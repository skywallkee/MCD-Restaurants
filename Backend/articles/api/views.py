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
from django.shortcuts import get_object_or_404

from datetime import datetime




class RestaurantViewSet(viewsets.ModelViewSet):
    serializer_class = RestaurantSerializer
    queryset = Restaurant.objects.all()

    def create(self,request):
        data = json.loads(request.body.decode('utf-8'))
        restaurant = Restaurant(nameR = data["nameR"], lungime = data["lungime"], latime = data["latime"], adresa = data["adresa"])
        restaurant.save()
        serializer=RestaurantSerializer(restaurant)
        return Response(serializer.data) 

    def retrieve(self,request,pk=None):
        restaurant=get_object_or_404(self.queryset, id=pk)
        serializer = RestaurantSerializer(restaurant)
        return Response(serializer.data)

    def destroy(self,request,pk=None):
        restaurant=get_object_or_404(self.queryset, id=pk)
        restaurant.delete()
        return Response({'message':"Restaurant deleted"}, status=status.HTTP_204_NO_CONTENT)

    def update(self, request,*args, **kwargs):
        instance = self.get_object()
        serializer = RestaurantSerializer(
        instance=instance,
        data=request.data
        )
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(serializer.data)

    @action(detail=False, methods=['get'],url_path="getRestaurant")
    def get_restaurant(self,request):
        data = request.query_params
        print("Intra")
        name=data["name"]
        restaurant=Restaurant.objects.raw("Select * from articles_restaurant where nameR LIKE '%%"+str(name)+"%%' ORDER BY nameR")
        print(restaurant)
        serializer=RestaurantSerializer(restaurant,many=True)
        return Response(serializer.data)


class ReviewViewSet(viewsets.ModelViewSet):
    serializer_class = ReviewSerializer
    queryset = Review.objects.all()

    def create(self,request):
        authorization = request.headers['Authorization']
        try:
            if(Token.objects.get(key=authorization)):
                data = json.loads(request.body.decode('utf-8'))
                review = Review(nr_stele = data["nr_stele"], mesaj = data["mesaj"], data = data["data"])
                review.id_R = Restaurant.objects.get(id = data["id_R"])
                review.id_U = User.objects.get(id = data["id_U"]) 
                review.save()
                serializer=ReviewSerializer(review)
                return Response(serializer.data) 
        except:
            return Response(status=401)

    def retrieve(self,request,pk=None):
        review=get_object_or_404(self.queryset, id=pk)
        serializer = ReviewSerializer(review)
        return Response(serializer.data)

    def destroy(self,request,pk=None):
        review=get_object_or_404(self.queryset, id=pk)
        review.delete()
        return Response({'message':"Review deleted"}, status=status.HTTP_204_NO_CONTENT)

    def update(self, request,*args, **kwargs):
        instance = self.get_object()
        serializer = ReviewSerializer(
        instance=instance,
        data=request.data
        )
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(serializer.data)

class RezervariViewSet(viewsets.ModelViewSet):
    serializer_class = RezervariSerializer
    queryset = Rezervari.objects.all()

    def create(self,request):
        authorization = request.headers['Authorization']
        try:
            if(Token.objects.get(key=authorization)):
                data = json.loads(request.body.decode('utf-8'))
                rezervare = Rezervari(data = data["data"],ora=data["ora"],timp=data["timp"],telefon=data["telefon"],nume_pers=data["nume_pers"],email=data["email"])
                rezervare.id_M = Mese.objects.get(id = data["id_M"])
                rezervare.id_U = User.objects.get(id = data["id_U"]) 
                rezervare.save()
                serializer=RezervariSerializer(rezervare)
                return Response(serializer.data) 
        except:
            return Response(status=401)

    def retrieve(self,request,pk=None):
        rezervare=get_object_or_404(self.queryset, id=pk)
        serializer = RezervariSerializer(rezervare)
        return Response(serializer.data)

    def destroy(self,request,pk=None):
        rezervare=get_object_or_404(self.queryset, id=pk)
        rezervare.delete()
        return Response({'message':"Reservation deleted"}, status=status.HTTP_204_NO_CONTENT)

    def update(self, request,*args, **kwargs):
        instance = self.get_object()
        serializer = RezervariSerializer(
        instance=instance,
        data=request.data
        )
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(serializer.data)

class PeretiViewSet(viewsets.ModelViewSet):
    serializer_class = PeretiSerializer
    queryset = Pereti.objects.all()

    def create(self,request):
        data = json.loads(request.body.decode('utf-8'))
        perete = Pereti(nume = data["nume"],etaj=data["etaj"],Ax=data["Ax"],Ay=data["Ay"],Bx=data["Bx"],By=data["By"],Cx=data["Cx"],Cy=data["Cy"],Dx=data["Dx"],Dy=data["Dy"])
        perete.id_R = Restaurant.objects.get(id = data["id_R"])
        perete.save()
        serializer=PeretiSerializer(perete)
        return Response(serializer.data) 

    def retrieve(self,request,pk=None):
        perete=get_object_or_404(self.queryset, id=pk)
        serializer = PeretiSerializer(perete)
        return Response(serializer.data)

    def destroy(self,request,pk=None):
        perete=get_object_or_404(self.queryset, id=pk)
        perete.delete()
        return Response({'message':"Wall deleted"}, status=status.HTTP_204_NO_CONTENT)

    def update(self, request,*args, **kwargs):
        instance = self.get_object()
        serializer = PeretiSerializer(
        instance=instance,
        data=request.data
        )
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(serializer.data)

class MeseViewSet(viewsets.ModelViewSet):
    serializer_class = MeseSerializer
    queryset = Mese.objects.all()

    def create(self,request):
        data = json.loads(request.body.decode('utf-8'))
        masa = Mese(nume = data["nume"],etaj=data["etaj"],Ax=data["Ax"],Ay=data["Ay"],Bx=data["Bx"],By=data["By"],Cx=data["Cx"],Cy=data["Cy"],
        Dx=data["Dx"],Dy=data["Dy"],nr_locuri=data["nr_locuri"])
        masa.id_R = Restaurant.objects.get(id = data["id_R"])
        masa.save()
        serializer=MeseSerializer(masa)
        return Response(serializer.data) 

    def retrieve(self,request,pk=None):
        masa=get_object_or_404(self.queryset, id=pk)
        serializer = MeseSerializer(masa)
        return Response(serializer.data)

    def destroy(self,request,pk=None):
        masa=get_object_or_404(self.queryset, id=pk)
        masa.delete()
        return Response({'message':"Table deleted"}, status=status.HTTP_204_NO_CONTENT)

    def update(self, request,*args, **kwargs):
        instance = self.get_object()
        serializer = MeseSerializer(
        instance=instance,
        data=request.data
        )
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(serializer.data)



@csrf_exempt
@api_view(['POST'])
def createUser(request):
    data = json.loads(request.body.decode('utf-8'))
    if request.method == "POST":
        try:
            if not data["password1"]==data["password2"]:
                raise Exception("The passwords are not the same")
            print("\n\nIntra\n\n")
            user=User.objects.create_user(data["username"],data["email"],data["password1"])
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
            token, created = Token.objects.get_or_create(user=user)
            return Response({'key': token.key})
        except Exception as er:
            print("Error: ",er)
            return Response({'error':str(er)},status=status.HTTP_400_BAD_REQUEST)