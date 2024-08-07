FROM node:alpine

COPY . /client

WORKDIR /client

EXPOSE 3000

RUN npm i

CMD [ "npm", "start" ]