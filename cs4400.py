from flask import Flask, flash, render_template, g, request, redirect, session
from flaskext.mysql import MySQL


app = Flask(__name__)

# Secret key is necessary for creating user sessions within the app
app.secret_key = 'D8K27qBS8{8*sYVU>3DA530!0469x{'

mysql = MySQL()
app.config['MYSQL_DATABASE_USER'] = 'cs4400_12'
app.config['MYSQL_DATABASE_PASSWORD'] = 'DjEn0kw9'
app.config['MYSQL_DATABASE_DB'] = 'cs4400_12'
app.config['MYSQL_DATABASE_HOST'] = 'academic-mysql.cc.gatech.edu'
mysql.init_app(app)

db = mysql.connect()
cursor = db.cursor()


@app.route('/')
def login():
    session['username'] = ''
    return render_template("login.html")


@app.route('/processLogin', methods=['GET', 'POST'])
def processLogin():
    username = request.form['username']
    password = request.form['password']
    cursor.execute("SELECT * "
                   "FROM `User` "
                   "WHERE Username = %s"
                   "AND PASSWORD = %s", [username, password])
    user_data = cursor.fetchone()
    print (user_data)
    if user_data:
        cursor.execute("SELECT * FROM `User`")
        print ("gud")
        usernames = cursor.fetchall()
        print (usernames)
        # return render_template('admin.html', usernames=usernames)
        userType = user_data[3]
        print (userType)
        if userType == "CITY_OFFICIAL":
            return render_template('cityOffHome.html')
        elif userType == "CITY_SCIENTIST":
            return render_template('citySciHome.html')
        else:
            return render_template('admin.html')
    else:
        session['username'] = username
        print ('yeet')
        flash("That bad", "error")
        return redirect('/')


@app.route('/showRegister', methods=['GET', 'POST'])
def showRegister():
    return render_template("register.html")


@app.route('/processRegister', methods=['GET', 'POST'])
def processRegister():
    username = request.form['username']
    password = request.form['password']
    confirmPassword = request.form['confirmPassword']
    emailAddress = request.form['email']
    userType = request.form['userType']
    print(userType)
    print(password)
    print(confirmPassword)
    if password == confirmPassword:
        print("in pass conf")
        cursor.execute("SELECT * "
                       "FROM `User` "
                       "WHERE Username = %s", [username])
        user_data = cursor.fetchone()
        print (user_data)
        if not user_data:
            print('on user exists')
            cursor.execute("INSERT INTO `User` "
                           "(`Username` ,"
                           "`EmailAddress` ,"
                           "`Password` ,"
                           "`UserType`) "
                           "VALUES ("
                           "%s, %s, %s, %s)",
                           [username, emailAddress, password, userType])
            db.commit()
            print('here')
            if userType is "CITY_OFFICIAL":
                title = "test"
                city = "test"
                state = "test"
                cursor.execute("INSERT INTO `City_Official` "
                               "(`Username` ,"
                               "`Title` ,"
                               "`Approved` ,"
                               "`City` ,"
                               "`State`) "
                               "VALUES ("
                               "%s, %s, NULL , %s, %s)",
                               [username, title, city, state])
                db.commit()
                return render_template("cityOffHome.html")
    return render_template("citySciHome.html")


@app.route('/showMessage')
def showMessage():
    print('showing message')
    # cursor.execute("SELECT `message` FROM `users` WHERE `username`=%s", [session.get('username')])
    # message = cursor.fetchone()
    return render_template('user.html', message="yeet")

if __name__ == '__main__':
    app.run()
