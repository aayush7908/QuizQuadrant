# stage - 1
FROM node:alpine as builder
WORKDIR /client
COPY . .
RUN yarn install
RUN npm run build

# stage - 2
FROM node:alpine
WORKDIR /client
COPY --from=builder /client .
EXPOSE 3000
CMD [ "npm", "start" ]