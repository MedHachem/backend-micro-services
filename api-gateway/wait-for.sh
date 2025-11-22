#!/bin/sh
# wait-for-eureka.sh
# Usage: ./wait-for-eureka.sh host port command

set -e

host="$1"
port="$2"
shift 2
cmd="$@"

echo "Waiting for $host:$port to be available..."

while ! nc -z "$host" "$port"; do
  echo "Waiting for $host:$port..."
  sleep 2
done

echo "$host:$port is available. Starting command: $cmd"
exec $cmd

