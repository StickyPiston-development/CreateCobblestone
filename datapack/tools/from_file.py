import os

# Specify the input file name
input_file = "generators.txt"

# Read the input file and process each line
with open(input_file, "r") as file:
    for line in file:
        # Split the line by the colon
        parts = line.strip().split(":")

        # Extract the ID from the line
        id = parts[1].strip()

        # Create the output file name
        output_file = f"{id}.json"

        # Create the content for the output file
        content = f"{{\n  \"block\": \"{line.strip("\n")}\"\n}}"

        # Write the content to the output file
        with open(output_file, "w") as output:
            output.write(content)

        print(f"Created file: {output_file}")
