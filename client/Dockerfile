FROM node:alpine

COPY . /client

WORKDIR /client

RUN npm i

RUN npm run build

ENTRYPOINT [ "npm", "start" ]