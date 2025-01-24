#!/bin/bash

export PATH=$PATH:/mnt/d/IdeaProjects/appium-mobile-automation

# Parameters
SLUG="$1"
URL="<url>"
METHOD="POST"
AUTH_HEADER="<Key>"

echo "Slug Number: $SLUG"
# Create JSON payload
JSON_PAYLOAD=$(cat <<EOF
{
    "event": {
        "nounce": "",
        "type": "",
        "created_at": ""
    },
    "application": {
        "form": "",
        "id": ,
        "no": "",
        "slug": "$SLUG",
        "submitted_at": "",
        "status": "",
        "other_status": {
            "ekyc": ""
        }
    },
    "answers": [
        {
            "question": "",
            "value": ""
        }
    ],
    "extra": {
        "ekyc": {
            "face_compare": {
                "status": "",
                "score":
            },
            "liveness": {
                "url": "",
                "attempts": [
                    {
                        "attempt": ,
                        "id": ,
                        "created_at": "",
                        "status": "",
                        "message": "",
                        "images": [
                            {
                                "url": "",
                                "action": ""
                            }
                        ]
                    }
                ]
            }
        }
    }
}
EOF
)

echo "Current directory: $(pwd)"

echo "Curl location: $(which curl)"

# Execute curl command and capture the response
response=$(curl -k -s -w "\nHTTP_STATUS:%{http_code}" -v -X "$METHOD" "$URL" \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d "$JSON_PAYLOAD" 2>&1)

# Extract the status code
http_status=$(echo "$response" | tail -n1)
response_body=$(echo "$response" | sed '$d')

# Output the status code
echo "Status code: ${http_status##*:}"

if [ -z "$http_status" ]; then
  echo "Error: No HTTP status received. Check network connection and API endpoint."
  exit 1
fi

# Check if response body is empty
if [ -z "$response_body" ]; then
    echo "Response body is empty"
else
    echo "Response body:"
    echo "$response_body"
fi

# Assert that the status code is 200

status_code=$(echo "$http_status" | cut -d ':' -f 2)

echo "status code: $status_code"

if [ "$status_code" -eq "200" ]; then
    echo "Assertion passed: Status code is 200"
else
    echo "Assertion failed: Expected status code 200, but got $http_status"
    exit 1
fi

echo "Shell script executed successfully"
