import requests
import re   

express_api = "http://localhost:3000/maven"
params = {
    "groupId": "", 
    "artifactId": "junit",
    "version": "4.1"
}
try:
    response = requests.get(express_api, params=params)
    # Check the response status code
    if response.status_code == 200:
        # Check if Content-Disposition header is present
        content_disposition = response.headers.get("Content-Disposition")
        if content_disposition:
            # Extract the filename from the Content-Disposition header using a regular expression
            match = re.search(r'filename="(.+)"', content_disposition)
            if match:
                filename = match.group(1)
            else:
                filename = f"{params['artifactId']}-{params['version']}.jar"
        else:
            filename = f"{params['artifactId']}-{params['version']}.jar"

        # Save the JAR file with the extracted filename
        with open(filename, "wb") as jar_file:
            jar_file.write(response.content)
        print(f"JAR file '{filename}' downloaded successfully.")
    
    elif response.status_code == 400:
        # The API returned a bad request response
        error_message = response.json()
        print("Error 400:", error_message)
    else:
        print(f"Failed to resolve the dependency. Status code: {response.status_code}")

except requests.exceptions.RequestException as e:
    # Handle network-related errors
    print("Network error:", e)

except Exception as e:
    # Handle other exceptions
    print("An error occurred:", e)


