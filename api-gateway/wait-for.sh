#!/bin/sh
# Usage:
#   ./wait-for-two.sh host1 port1 host2 port2 command...

set -e

host1="$1"
port1="$2"
host2="$3"
port2="$4"
shift 4
cmd="$@"

echo "Waiting for $host1:$port1 ..."
while ! nc -z "$host1" "$port1"; do
  echo "Still waiting for $host1:$port1 ..."
  sleep 2
done
echo "$host1:$port1 is UP."

echo "Waiting for $host2:$port2 ..."
while ! nc -z "$host2" "$port2"; do
  echo "Still waiting for $host2:$port2 ..."
  sleep 2
done
echo "$host2:$port2 is UP."

echo "Both services READY. Starting command: $cmd"
exec $cmd

