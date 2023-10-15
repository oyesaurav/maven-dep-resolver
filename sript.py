import requests

express_api = "http://localhost:3000/maven"
params = {
    "groupId": "com.example", 
    "artifactId": "my-library",
    "version": "1.0.0"
}

try:
    response = requests.get(express_api, params=params)

    # Check the response status code
    if response.status_code == 200:
        # The request was successful
        data = response.json()
        print("Maven Dependency Information:")
        print(f"Group ID: {data['groupId']}")
        print(f"Artifact ID: {data['artifactId']}")
        print(f"Version: {data['version']}")
    elif response.status_code == 400:
        # The API returned a bad request response
        print("Invalid API request. Please check the parameters.")
    else:
        # Handle other HTTP status codes here
        print(f"API request failed with status code {response.status_code}")

except requests.exceptions.RequestException as e:
    # Handle network-related errors
    print("Network error:", e)

except Exception as e:
    # Handle other exceptions
    print("An error occurred:", e)
