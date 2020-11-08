from django.shortcuts import render
from rest_framework import viewsets
from rest_framework.authtoken.models import Token
from rest_framework.response import Response
from rest_framework import status
from rest_framework.decorators import api_view, action
from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth.models import User
from django.http import HttpResponse
from django.http.response import JsonResponse
from rest_framework.parsers import JSONParser
from rest_framework.exceptions import APIException

from django.contrib.auth import get_user_model
import json


# Create your views here.

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