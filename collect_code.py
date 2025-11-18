import os

def collect_all_code(input_path, output_file):
    print(f"Starting to collect code from {input_path}")
    file_count = 0
    with open(output_file, 'w', encoding='utf-8') as outfile:
        for root, _, files in os.walk(input_path):
            for filename in files:
                if filename.endswith('.java'):
                    file_count += 1
                    filepath = os.path.join(root, filename)
                    print(f"Reading {filepath}")
                    with open(filepath, 'r', encoding='utf-8') as infile:
                        outfile.write(f'\n// ---- Start of {filename} ----\n')
                        outfile.write(infile.read())
                        outfile.write(f'\n// ---- End of {filename} ----\n\n')
    print(f"Finished. {file_count} files processed. Output saved to {output_file}")

# Run the function with chosen paths
collect_all_code(r'src\main\java\com\shopease\backend', 'all_code.txt')
