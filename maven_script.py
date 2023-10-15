import subprocess

def resolve_maven_dependency(group_id, artifact_id, version):
    try:
        # Define the command to run the Java program with arguments
        java_command = "java"
        jar_file = "C:\trial projects\Secure Blink Task\target\local-repo\junit\junit\4.11\junit-4.11.jar"  # Update with the actual path
        args = [java_command, "-jar", jar_file, group_id, artifact_id, version]

        # Execute the Java program as a subprocess
        process = subprocess.Popen(args, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

        # Get the output and error (if any) from the Java program
        stdout, stderr = process.communicate()

        # Check for errors
        if stderr:
            print(f"Error: {stderr}")
        else:
            print(f"Output: {stdout}")
    except Exception as e:
        print(f"An error occurred: {e}")

# Example usage
resolve_maven_dependency("junit", "junit", "4.12")
