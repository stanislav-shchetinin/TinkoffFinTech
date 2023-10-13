import json

def count_space(cnt):
    return ' '*cnt

json_input = ""
with open('russian-cities.json', encoding="utf-8") as f:
    json_input = f.read()

obj = json.loads(json_input)

yaml_result = f"databaseChangeLog:\n{count_space(2)}-{count_space(2)}changeSet:\n{count_space(7)}id: insert-into-city\n{count_space(7)}author: Stas\n{count_space(7)}changes:\n"

for x in obj:
    city = x["name"]
    yaml_result += f"{count_space(9)}- insert:\n{count_space(13)}tableName: city\n{count_space(13)}columns:\n{count_space(15)}- column:\n{count_space(19)}name: name\n{count_space(19)}value: \"{city}\"\n"

f = open('insert-into-city.yml','w', encoding="utf-8")
f.write(yaml_result)
f.close()