from django.db import models
from django.contrib.auth.models import User

# Create your models here.

# class Article(models.Model):
#     title = models.CharField(max_length=120)
#     content = models.TextField()

#     def __str__(self):
#         return self.title


# class User(models.Model):
#     user = models.CharField(max_length=250)
#     password = models.CharField(max_length=250)
#     email = models.CharField(max_length=250)

#     def _str_(self):
#         return self.user + ' ' + self.password + ' ' + self.email


class Restaurant(models.Model):
    nameR = models.CharField(max_length=250)
    lungime = models.IntegerField()
    latime = models.IntegerField()
    adresa = models.CharField(max_length=250)

    def _str_(self):
        return self.nameR + ' ' + self.lungime + ' ' + self.latime + ' ' + self.adresa



class Pereti(models.Model):
    id_R = models.ForeignKey(Restaurant, on_delete=models.CASCADE)
    nume = models.IntegerField(null=True)
    etaj = models.CharField(max_length=250)
    Ax = models.IntegerField()
    Ay = models.IntegerField()
    Bx = models.IntegerField()
    By = models.IntegerField()
    Cx = models.IntegerField()
    Cy = models.IntegerField()
    Dx = models.IntegerField()
    Dy = models.IntegerField()

    def _str_(self):
        return self.id_R + ' ' + self.nume + ' '+ self.etaj + ' ' + self.Ax + ' '+ self.Ay + ' '+self.Bx + ' ' + self.By + ' '+self.Cx + ' '+self.Cy + ' '+self.Dx + ' '+self.Dy

class Mese(models.Model):
    id_R = models.ForeignKey(Restaurant, on_delete=models.CASCADE)
    nume = models.IntegerField()
    etaj = models.CharField(max_length=250)
    Ax = models.IntegerField()
    Ay = models.IntegerField()
    Bx = models.IntegerField()
    By = models.IntegerField()
    Cx = models.IntegerField()
    Cy = models.IntegerField()
    Dx = models.IntegerField()
    Dy = models.IntegerField()
    nr_locuri = models.IntegerField()


    def _str_(self):
        return self.id_R + ' ' + self.nume + ' '+ self.etaj + ' ' + self.Ax + ' '+ self.Ay + ' '+self.Bx + ' ' + self.By + ' '+self.Cx + ' '+self.Cy + ' '+self.Dx + ' '+self.Dy + ' '+ self.nr_locuri

class Rezervari(models.Model):
    id_M = models.ForeignKey(Mese, on_delete = models.CASCADE)
    id_U = models.ForeignKey(User, on_delete = models.CASCADE, blank=True,null=True)
    data = models.DateField(auto_now=True)
    ora = models.TimeField(auto_now=True)
    timp = models.TimeField()
    telefon = models.CharField(max_length=14)
    nume_pers = models.CharField(max_length=250)
    email = models.CharField(max_length=250)


    def _str_(self):
        return self.id_M + ' ' + self.id_U + ' '+ self.data +' '+ self.ora +' '+self.timp +' '+ self.telefon +' '+ self.nume_pers +' '+self.email


class Review(models.Model):
    id_R = models.ForeignKey(Restaurant, on_delete= models.CASCADE)
    id_U = models.ForeignKey(User, on_delete=models.CASCADE)
    nr_stele = models.IntegerField()
    mesaj = models.CharField(max_length=2000)
    data = models.DateTimeField(auto_now=True)

    def _str_(self):
        return self.id_R +' '+ self.id_U + ' '+ self.nr_stele +' ' + self.mesaj +' '+ self.data