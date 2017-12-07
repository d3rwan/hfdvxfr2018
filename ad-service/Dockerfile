FROM node:latest

# Create app directory
RUN mkdir -p /app
WORKDIR /app

# Install app dependencies
COPY . /app
RUN npm install

EXPOSE 3000
CMD [ "npm", "start" ]
