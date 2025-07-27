<%-- 
    Document   : newjsp
    Created on : 22 Jul 2025, 8:18:11 pm
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!-- Compiled and minified CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

        <!-- Compiled and minified JavaScript -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>

    </head>
    <body style="background: url(myImage/pexels-veeterzy-303383.jpg);background-size: cover;background-attachment: fixed;">

        <div class="container">
            <div class="row">
                <div class="col m6 offset-m3"> 
                    <div class="card">
                        <div class="card-content">
                            <h3 style="margin-top: 10px;" class="center-align">Register here !</h3>
                            <h5 id="msg" class="center-align"></h5>
                            <div class="form center-align"  >
                                <form action="Register" method="POST" id="myform"  enctype="multipart/form-data">
                                    <input type="text" name="user_name" placeholder="Enter your name"/>
                                    <input type="Password" name="user_password" placeholder="Enter your Password"/>
                                    <input type="email" name="user_mail" placeholder="Enter your email_id"/>

                                    <div class="file-field input-field">
                                        <div class="btn">
                                            <span>File</span>
                                            <input name="image" type="file">
                                        </div>
                                        <div class="file-path-wrapper">
                                            <input class="file-path validate" type="text">
                                        </div>
                                    </div>

                                    <button type="submit" class="btn">Submit</button>
                                </form>

                            </div>
                            <div class="loader center-align" style="margin-top: 10px;display: none;"><div class="preloader-wrapper big active">
                                    <div class="spinner-layer spinner-blue-only">
                                        <div class="circle-clipper left">
                                            <div class="circle"></div>
                                        </div><div class="gap-patch">
                                            <div class="circle"></div>
                                        </div><div class="circle-clipper right">
                                            <div class="circle"></div>
                                        </div>
                                    </div>
                                </div>


                                <h5>Please wait...</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script
            src="https://code.jquery.com/jquery-3.7.1.min.js"
            integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
        crossorigin="anonymous"></script>

        <script>
            $(document).ready(function () {
                console.log("page is ready.......");

                $("#myform").on('submit', function (event) {
                    event.preventDefault();
//                    var f = $(this).serialize();
                    //let f = new FormData(this);
                    let f = new FormData(document.getElementById("myform"));

                    console.log(f);

                    $(".loader").show();
                    $(".form").hide();

                    $.ajax({
                        url: "Register",
                        data: f,
                        type: "POST",
                         processData: false, // ✅ MUST be outside of success/error blocks
                         contentType: false, // ✅ same here
                        success: function (data, textstatus, jqXHR) {
                            console.log(data);
                            console.log("SUCCESS....");

                            $(".loader").hide();
                            $(".form").show();
                            if (data.trim() === 'Done') {
                                $('#msg').html("Successfully Registered !!");
                                $('#msg').addClass('Green-text');
                            } else {
                                $('#msg').html("Something Went wrong !!");
                                $('#msg').addClass('red-text');

                            }
                        },
                        error: function (jqXHR, textstatus, errorThrown) {
                            console.log(data);
                            console.log("FAILURE....");

                            $(".loader").hide();
                            $(".form").show();

                            $('#msg').html("Something Went wrong !!");
                        }
                    });


                });
            });

        </script>
    </body>
</html>
