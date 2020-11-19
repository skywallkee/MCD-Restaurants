from rest_framework.routers import DefaultRouter
from articles.api.views import (
   RestaurantViewSet, 
    ReviewViewSet, 
    RezervariViewSet, 
    PeretiViewSet,
    MeseViewSet
)
# from django.urls import path, include
# from server.api import views

router = DefaultRouter()
router.register(r'restaurant', RestaurantViewSet, basename='restaurant')
router.register(r'review', ReviewViewSet, basename='review')
router.register(r'rezervari', RezervariViewSet, basename='rezervari')
router.register(r'pereti', PeretiViewSet, basename='pereti')
router.register(r'mese', MeseViewSet, basename='mese')
urlpatterns = router.urls
