<<<<<<< HEAD
=======
<%@ page import ="java.util.HashMap"%>
>>>>>>> wang
<%@ page import ="jar.bean.UserBean"%>
<!doctype html>
<html lang="en">
<head>
    <title>main</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="/microproject/static/css/bootstrap.min.css">
    <script src="/microproject/static/js/jquery.min.js"></script>
    <script src="/microproject/static/js/popper.js"></script>
    <script src="/microproject/static/js/bootstrap.min.js"></script>
    <script src='/microproject/static/js/page.js'></script>
</head>
<body>
<div class="container">
    <fieldset>
        <nav class="navbar navbar-inverse" role="navigation">
            <div class="container-fluid">
<<<<<<< HEAD
                <div class="navbar-header"><a class="navbar-brand">${user.getUsername()}</a></div>
=======
                <div class="navbar-header"><a class="navbar-brand">${user.username}</a></div>
>>>>>>> wang
                <div>
                    <ul class="nav navbar-nav">
                        <li><a href="${pageContext.request.contextPath}/Gopage?page=mainPage">Home</a></li>
                        <li><a href="${pageContext.request.contextPath}/Gopage?page=profile" class="text-success">Profile</a></li>
<<<<<<< HEAD
                        <li><a href="${pageContext.request.contextPath}/Service?method=getCommandes" class="text-success">Commandes</a></li>
                        <li><a href="${pageContext.request.contextPath}/Service?method=getRessources" class="text-success">Your houses</a></li>
                        <li><a href="${pageContext.request.contextPath}/Client?method=Logout" class="text-success">Disconnect</a></li>
=======
                        <li><a href="${pageContext.request.contextPath}/Service?Demandes&id=${user.id}" class="text-success">Commandes</a></li>
                        <li><a href="${pageContext.request.contextPath}/Service?Demandes&id=${user.id}" class="text-success">Your houses</a></li>
                        <li><a href="${pageContext.request.contextPath}/Client?method=Logout" class="text-success">Deconnexion</a></li>
>>>>>>> wang
                    </ul>
                </div>
            </div>
        </nav>
        <%-- information --%>
        <%if(request.getAttribute("info")!=null) {%>
            <div id="alert" class="alert alert-<%=request.getAttribute("type")%>"><%=request.getAttribute("info")%></div>
        <%}%>

        <div id="createDemande">
            <legend>Get a house or room</legend>
<<<<<<< HEAD
            <form action="${pageContext.request.contextPath}/Service?method=createSearch" method="post">
=======
            <form action="/Service?createDemande/" method="post">
>>>>>>> wang
                <div id="inputDemande">
                    <div id="plan1">
                        <div class="form-group">
                            <label>Destination</label>
                            <input name="destination" type="text" class="form-control" required
                                   placeholder="destination"/>
                        </div>
                        <div class="form-group">
                            <label>Check in date</label>
<<<<<<< HEAD
                            <input name="checkin" type="date" class="form-control" required
=======
                            <input name="checkin1" type="date" class="form-control" required
>>>>>>> wang
                                   placeholder="checkin date"/>
                        </div>
                        <div class="form-group">
                            <label>Check out date</label>
<<<<<<< HEAD
                            <input name="checkout" type="date" class="form-control" required
=======
                            <input name="checkout1" type="date" class="form-control" required
>>>>>>> wang
                                   placeholder="checkout date"/>
                        </div>
                        <div class="form-group">
                            <label>Number of people</label>
<<<<<<< HEAD
                            <input name="nb" type="number" class="form-control" required
=======
                            <input name="nb1" type="number" class="form-control" required
>>>>>>> wang
                                   placeholder="How many people ?"/>
                        </div>
                        <div class="form-group">
                            <label>Type: </label>
                            <input name="type" id="id_room" value="room" type="radio" checked>Room
                            <input name="type" id="id_house" value="house" type="radio"/>House
                        </div>
<<<<<<< HEAD
                        <div class="form-group">
                            <label>Smoker: </label>
                            <select name="smoker" id="id_smoker" class="form-control"
                                    placeholder="room's type" required>
                                <option value="Smoker">Yes</option>
                                <option value="No Smoker">No</option>
                            </select>
=======
                        <div id="optionCh1">
                            <div class="form-group">
                                <label>Level: </label>
                                <select name="level" id="id_level" class="form-control"
                                        placeholder="room's level" required>
                                    <option value="Standard">Standard</option>
                                    <option value="Premium">Premium</option>
                                    <option value="President">President</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Smoker: </label>
                                <select name="smoker" id="id_smoker" class="form-control"
                                        placeholder="room's type" required>
                                    <option value="Smoker">Yes</option>
                                    <option value="No Smoker">No</option>
                                </select>
                            </div>
>>>>>>> wang
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary float-right">Go</button>
            </form>
        </div>
    </fieldset>
</div>
</body>
</html>
