import pickle

def save_data(variable, part="save", filename='save.pkl'):
    try:
        with open(filename, 'rb') as file:
            data = pickle.load(file)
    except FileNotFoundError:
        data = {}
    data[part] = variable
    with open(filename, 'wb') as file:
        pickle.dump(data, file)

def load_part(part="save", filename='save.pkl'):
    try:
        with open(filename, 'rb') as file:
            data = pickle.load(file)
            return data.get(part, None)
    except FileNotFoundError:
        return None

def load_data(filename='save.pkl'):
    try:
        with open(filename, 'rb') as file:
            return pickle.load(file)
    except FileNotFoundError:
        return None

if __name__ == "__main__":
    print(load_data())