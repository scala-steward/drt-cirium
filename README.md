# DRT Cirium feed service

This service ingests Cirium flight data and provides a REST API for querying flight information.

## Overview
This codebase contains a Scala backend built using sbt. The service fetches flight data from Cirium and exposes it via RESTful APIs.

## Scala Backend
To run the backend, enter the root of the codebase and run:

```bash
JAVA_OPTS="-Xms1g -Xmx1g" \
TZ=UTC \
PORT_CODES=PIK,STN \
CIRIUM_APP_ENTRY_POINT=https://api.flightstats.com/flex/flightstatusfeed/rest/v2/json/latest \
CIRIUM_FLIGHT_RETENTION_HOURS=24 \
CIRIUM_MESSAGE_LATENCY_TOLERANCE_SECONDS=60 \
CIRIUM_LOST_CONNECTION_TOLERANCE_SECONDS=300 \
CIRIUM_APP_ID=<secret> \
CIRIUM_APP_KEY=<secret> \
GO_BACK_HOURS=3 \
CIRIUM_POLL_MILLIS=2000 \
NO_JSON_LOGGING= \
sbt run | tee cirium.log
```

Make sure to replace `<secret>` with your actual Cirium API credentials from Kubernetes secrets.

