```sh
kubectl create secret docker-registry crossnetcorp-regcred \
  -n microservices \
  --docker-username=XXXXXXXXXX \
  --docker-password=XXXXX \
  --docker-server=https://index.docker.io/v1/
```
