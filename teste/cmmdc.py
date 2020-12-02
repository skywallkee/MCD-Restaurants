  
def calcul(a,b):
  if a<=0 or b<=0:
    return 0
  r=a%b
  while r:
    a=b
    b=r
    r=a%b
  return b
