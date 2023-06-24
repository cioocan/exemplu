import ast
import sys


def format_input(string):
    string_list = ast.literal_eval(string)
    return [elem for elem in string_list]


def main():
    dict_result = {}
    for line in sys.stdin:
        data = format_input(line)

        word = data[0]
        file_dict = data[1]

        if word not in dict_result:
            dict_result[word] = {}

        for file_name, count in file_dict.items():
            if file_name not in dict_result[word]:
                dict_result[word][file_name] = 0

            dict_result[word][file_name] += count

    with open("result.txt", "w") as fp:
        for key in dict_result:
            fp.write("<{}, {}>\n".format(key, dict_result[key]))


if __name__ == "__main__":
    main()
