FROM node:20-alpine AS build

WORKDIR /app

COPY myblog-frontend/package.json myblog-frontend/package-lock.json ./
RUN npm ci

COPY myblog-frontend .

ARG VITE_API_BASE=/api
ENV VITE_API_BASE=${VITE_API_BASE}

RUN npm run build

FROM nginx:1.27-alpine

ENV PORT=80
ENV API_UPSTREAM=http://blog-gateway.railway.internal:8080

COPY myblog-frontend/nginx/default.conf.template /etc/nginx/templates/default.conf.template
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
