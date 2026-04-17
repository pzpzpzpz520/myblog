#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

cd "$ROOT_DIR"

if [[ ! -f .env ]]; then
  echo "Missing .env file. Copy .env.prod.example to .env and fill in production values first."
  exit 1
fi

docker compose --env-file .env -f docker-compose.prod.yml up -d --build --remove-orphans
