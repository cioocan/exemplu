import string
import sys


def main():
    cli_arguments = sys.argv[1:]
    for file_name in cli_arguments:
        with open(file_name, "r") as f:
            content = f.read().lower()
            words = [word.strip() for word in content.split()]

            pairs = map(lambda word: [word, {file_name: 1}], words)

            for pair in pairs:
                print(pair)


if __name__ == "__main__":
    main()

