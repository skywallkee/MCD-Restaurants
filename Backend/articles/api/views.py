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
import datetime, calendar
from datetime import date




class RestaurantViewSet(viewsets.ModelViewSet):
    serializer_class = RestaurantSerializer
    queryset = Restaurant.objects.all()

    def create(self,request):
        data = json.loads(request.body.decode('utf-8'))
        restaurant = Restaurant(nameR = data["nameR"], lungime = data["lungime"], latime = data["latime"], adresa = data["adresa"]
        ,poza= data["poza"],ora_deschidere= data["ora_deschidere"],ora_inchidere=data["ora_inchidere"])
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
            token = Token.objects.get(key=authorization)
            if(token):
                data = json.loads(request.body.decode('utf-8'))
                review = Review(nr_stele = data["nr_stele"], mesaj = data["mesaj"], data = data["data"])
                review.id_R = Restaurant.objects.get(id = data["id_R"])
                review.id_U = token.user
                review.save()
                serializer=ReviewSerializer(review)
                return Response(serializer.data) 
        except Exception as er:
            return Response({'error':str(er)},status=status.HTTP_401_UNAUTHORIZED)

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
            token = Token.objects.get(key=authorization)
            if(token):
                data = json.loads(request.body.decode('utf-8'))
                rezervare = Rezervari(data = data["data"],ora=data["ora"],timp=data["timp"],telefon=data["telefon"],nume_pers=data["nume_pers"],email=data["email"])
                rezervare.id_M = Mese.objects.get(id = data["id_M"])
                rezervare.id_U = token.user
                rezervare.save()
                serializer=RezervariSerializer(rezervare)
                return Response(serializer.data) 
        except Exception as er:
            return Response({'error':str(er)},status=status.HTTP_401_UNAUTHORIZED)

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

    @action(detail=False, methods=['post'])
    def getWalls(self,request):
        data=json.loads(request.body.decode('utf-8'))
        if request.method == "POST":
            try:
                walls=Pereti.objects.raw("Select * from articles_pereti where \"id_R_id\"="+str(data["id"]))

                serializer_walls=PeretiSerializer(walls,many=True)
                print("\n",serializer_walls.data)
                return Response(serializer_walls.data)
            except Exception as er:
                return Response({'error':str(er)},status=status.HTTP_400_BAD_REQUEST)
            


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

    @action(detail=False, methods=['post'])
    def getTables(self,request):
        data=json.loads(request.body.decode('utf-8'))
        if request.method == "POST":
            try:
                tables=Mese.objects.raw("Select * from articles_mese where \"id_R_id\"="+str(data["id"]))

                serializer_tables=MeseSerializer(tables,many=True)
                print("\n",serializer_tables.data)
                return Response(serializer_tables.data)
            except Exception as er:
                return Response({'error':str(er)},status=status.HTTP_400_BAD_REQUEST)

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

@api_view(['POST'])
def changePassword(request):
    authorization = request.headers['Authorization']
    data = json.loads(request.body.decode('utf-8'))
    if request.method == "POST":
        try:
            u = Token.objects.get(key=authorization).user
            print("Ajunge aici")
            user = authenticate(username=u.username,password=data["password"])
            if not  user:
                raise Exception("Invalid password")
            else:
                u.set_password(data["newPassword"])
                u.save()
            return Response("Password changed",status=status.HTTP_200_OK)
        except Exception as er:
            print("Error: ",er)
            return Response({'error':str(er)},status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def getReviewAverage(request):
    if request.method == 'POST':
        try:
            data=json.loads(request.body.decode('utf-8'))
            reviews=Review.objects.raw("Select * from articles_review where \"id_R_id\"="+str(data["id"]))
            serialzer_reviews=ReviewSerializer(reviews,many=True)
            sum_of_reviews=0
            
            for review in serialzer_reviews.data:
                sum_of_reviews=sum_of_reviews+int(review["nr_stele"])

            if len(serialzer_reviews.data) == 0 :
                return Response({'Average': 0})

            average=sum_of_reviews/len(serialzer_reviews.data)
            print("Medie: ", average)

            return Response({'Average': average})
        except Exception as er:
            return Response({'error':str(er)},status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def statisticsByDay(request):
    if request.method == 'POST':
        try:
            data=json.loads(request.body.decode('utf-8'))
            rezervation_filter_by_day_of_week=numberOfRezervationByDay(data["id"])

            return Response(
                {'Monday': len(rezervation_filter_by_day_of_week[0]),
                'Tuesday': len(rezervation_filter_by_day_of_week[1]),
                'Wednesday': len(rezervation_filter_by_day_of_week[2]),
                'Thursday': len(rezervation_filter_by_day_of_week[3]),
                'Friday': len(rezervation_filter_by_day_of_week[4]),
                'Saturday': len(rezervation_filter_by_day_of_week[5]),
                'Sunday': len(rezervation_filter_by_day_of_week[6])})
        except Exception as er:
            return Response({'error':str(er)},status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def statisticsByDayByHour(request):
    if request.method == 'POST':
        try:
            data=json.loads(request.body.decode('utf-8'))
            rezervation_filter_by_day_of_week=numberOfRezervationByDay(data["id"])
            rezerv_hours=[[],[],[],[],[],[],[],[],[],[],[],[]]
            for rezv in rezervation_filter_by_day_of_week[int(data["id_day"])]:
                time=datetime.datetime.strptime(rezv["ora"],'%H:%M:%S.%f')
                ora=datetime.time(int(time.strftime('%H')),int(time.strftime('%M')),int(time.strftime('%S')))
                if datetime.time(0,0,0)>=ora and ora<datetime.time(2,0,0):
                    rezerv_hours[0].append(rezv)
                    continue
                if datetime.time(2,0,0)>=ora and ora<datetime.time(4,0,0):
                    rezerv_hours[1].append(rezv)
                    continue
                if datetime.time(4,0,0)>=ora and ora<datetime.time(6,0,0):
                    rezerv_hours[2].append(rezv)
                    continue
                if datetime.time(6,0,0)>=ora and ora<datetime.time(8,0,0):
                    rezerv_hours[3].append(rezv)
                    continue
                if datetime.time(8,0,0)>=ora and ora<datetime.time(10,0,0):
                    rezerv_hours[4].append(rezv)
                    continue
                if datetime.time(10,0,0)>=ora and ora<datetime.time(12,0,0):
                    rezerv_hours[5].append(rezv)
                    continue
                if datetime.time(12,0,0)>=ora and ora<datetime.time(14,0,0):
                    rezerv_hours[6].append(rezv)
                    continue
                if datetime.time(14,0,0)>=ora and ora<datetime.time(16,0,0):
                    rezerv_hours[7].append(rezv)
                    continue
                if datetime.time(16,0,0)>=ora and ora<datetime.time(18,0,0):
                    rezerv_hours[8].append(rezv)
                    continue
                if datetime.time(18,0,0)>=ora and ora<datetime.time(20,0,0):
                    rezerv_hours[9].append(rezv)
                    continue
                if datetime.time(20,0,0)>=ora and ora<datetime.time(22,0,0):
                    rezerv_hours[10].append(rezv)
                    continue
                if datetime.time(22,0,0)>=ora and ora<datetime.time(0,0,0):
                    rezerv_hours[11].append(rezv)
                    continue
            

            return Response(
                {
                '0-2': len(rezerv_hours[0]),
                '2-4': len(rezerv_hours[1]),
                '4-6': len(rezerv_hours[2]),
                '6-8': len(rezerv_hours[3]),
                '8-10': len(rezerv_hours[4]),
                '10-12': len(rezerv_hours[5]),
                '12-14': len(rezerv_hours[6]),
                '14-16': len(rezerv_hours[7]),
                '16-18': len(rezerv_hours[8]),
                '18-20': len(rezerv_hours[9]),
                '20-22': len(rezerv_hours[10]),
                '22-24': len(rezerv_hours[11])
                }
                
                )
        except Exception as er:
            return Response({'error':str(er)},status=status.HTTP_400_BAD_REQUEST)



def numberOfRezervationByDay(id):
    today_date=datetime.date.today()
    last_month=today_date.month-1
    year = today_date.year
    if last_month==0:
        last_month=12
        year = year-1
    last_month_date=date(year,last_month,today_date.day+1)
    rezervari=Rezervari.objects.raw("Select * from articles_rezervari where data >= '"+str(last_month_date)+"' and data <= '"+str(today_date)+"'")
    serializer=RezervariSerializer(rezervari,many=True)
    rezervation_filter_by_day_of_week=[[],[],[],[],[],[],[]]
    for rezv in serializer.data:
        masa=Mese.objects.raw("Select * from articles_mese where id="+str(rezv['id_M']))
        serialzer_table=MeseSerializer(masa,many=True)
        if serialzer_table.data[0]["id_R"]==id:
            rezervation_filter_by_day_of_week[datetime.datetime.strptime(rezv["data"],'%Y-%m-%d').weekday()].append(rezv)

    return rezervation_filter_by_day_of_week

@api_view(['POST'])
def getRezervareForRestaurant(request):
    if request.method == 'POST':
        try:
            data=json.loads(request.body.decode('utf-8'))
            mese = Mese.objects.raw("Select * from articles_mese where \"id_R_id\"="+str(data["id"]))
            rezervari=Rezervari.objects.raw("Select * from articles_rezervari where data='"+str(data["data"])+"'")
            rezervari_bune = []
            
            serializer_rezervari=RezervariSerializer(rezervari,many=True)
            print("Rezervari1: ", serializer_rezervari.data)
            serializer_mese=MeseSerializer(mese,many=True)
            print("Mese: ", serializer_mese.data)

            for rez in serializer_rezervari.data:
                for masa in serializer_mese.data:
                    print(masa)
                    if masa["id"] == rez["id_M"]:
                        rezervari_bune.append(rez)

            print("Rezervari: ", rezervari_bune)
            return Response(rezervari_bune)
        except Exception as er:
            return Response({'error':str(er)},status=status.HTTP_400_BAD_REQUEST)