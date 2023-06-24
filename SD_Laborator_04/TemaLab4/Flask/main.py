from flask import Flask, render_template, request
import distutils.cmd
import requests

app = Flask(__name__)


@app.route('/', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        # Send login request to backend API
        url = 'http://localhost:8080/api/login'  # Change this to the URL of your Kotlin with Spring backend API
        data = {'username': request.form['username'], 'password': request.form['password']}
        response = requests.post(url, json=data)

        # Process response from backend API
        if response.status_code == 200:
            return 'Login successful!'
        else:
            return 'Invalid username or password'
    else:
        return render_template('login.html')


if __name__ == '__main__':
    app.run()