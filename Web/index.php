<?php
error_reporting(0);
if(isset($_POST['submit'])){
    $name=$_POST['name'];
    $email=$_POST['email'];
    $sub=$_POST['sub'];
    $msg=$_POST['message'];
    
    $to='info@cybersectnt.com';
    $subject="Form Submission: ".$sub;
    $subject2="Copy of your form submission".$sub;
    $message="Name: ".$name."\n\n"."Wrote the following message:\n\n".$msg;
    $message2="Here is a copy of your submission:\n\n"."Name: ".$name."\n\n"."Message:\n\n".$msg;
    $headers="From:".$email;
    $headers2="From:".$to;
    
    mail($to,$subject,$message,$headers);
    mail($email,$subject2,$message2,$headers2);
    echo"<h2>Mail Sent! Thank you ".$name." we will contact you shortly.</h2>";
}
?>
<!DOCTYPE html>
<html lang="en-US">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>CyberSecTNT</title>

    <!-- Favicon -->
    <link rel="icon" href="assets/img/favicon3.png">
    <!-- Bootstrap CSS -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">

    <!-- Slick Slider -->
    <link href="assets/css/slick.css" rel="stylesheet">
    <link href="assets/css/slick-theme.css" rel="stylesheet">

    <!-- AOS CSS -->
    <link href="assets/css/aos.css" rel="stylesheet">

    <!-- Lity CSS -->
    <link href="assets/css/lity.min.css" rel="stylesheet">

    <!-- Font Awesome CSS -->
    <link rel="stylesheet" href="assets/css/fontawesome-all.min.css">

    <!-- linearicons CSS -->
    <link rel="stylesheet" href="assets/css/linearicons.css">

    <!-- Our Min CSS -->
    <link href="assets/css/main.css" rel="stylesheet">

    <!-- Themes * You can select your color from color-1 , color-2 , color-3 , color-4 , ..etc * -->
    <link href="assets/css/color-1.css" rel="stylesheet">
    <!-- <link href="assets/css/color-1.css" rel="stylesheet"> -->
    <!-- <link href="assets/css/color-2.css" rel="stylesheet"> -->
    <!-- <link href="assets/css/color-3.css" rel="stylesheet"> -->
    <!-- <link href="assets/css/color-4.css" rel="stylesheet"> -->
    <!-- <link href="assets/css/color-5.css" rel="stylesheet"> -->
    <!-- <link href="assets/css/color-6.css" rel="stylesheet"> -->
    <!-- <link href="assets/css/color-7.css" rel="stylesheet"> -->
    <!-- <link href="assets/css/color-8.css" rel="stylesheet"> -->
    <!-- <link href="assets/css/color-9.css" rel="stylesheet"> -->
    <!-- <link href="assets/css/color-10.css" rel="stylesheet"> -->
    <!-- <link href="assets/css/color-11.css" rel="stylesheet"> -->
    <!-- <link href="assets/css/color-12.css" rel="stylesheet"> -->
    <!-- <link href="assets/css/color-13.css" rel="stylesheet"> -->
    <!-- <link href="assets/css/color-14.css" rel="stylesheet"> -->
    <!-- <link href="assets/css/color-15.css" rel="stylesheet"> -->
    <!-- <link href="assets/css/color-16.css" rel="stylesheet"> -->
    <!-- <link href="assets/css/color-17.css" rel="stylesheet"> -->
    <!-- <link href="assets/css/color-18.css" rel="stylesheet"> -->
    <!-- <link href="assets/css/color-19.css" rel="stylesheet"> -->
    <!-- <link href="assets/css/color-20.css" rel="stylesheet"> -->

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
</head>

<body data-spy="scroll" data-target="#main_menu" data-offset="70">
    <!-- Start Preloader -->
    <div class="preloader">
        <div class="loader-wrapper">
            <div class="loader"></div>
        </div>
    </div>
    <!-- End Preloader -->

    <!-- Start Header -->
    <header class="foxapp-header">
        <nav class="navbar navbar-expand-lg navbar-light" id="foxapp_menu">
            <div class="container">
                <a class="navbar-brand" href="index.html">
                    <img src="assets/img/Webp.net-resizeimage.gif" class="img-fluid" alt="">
                </a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#main_menu"
                    aria-controls="main_menu" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="main_menu">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item">
                            <a class="nav-link anchor active" href="#slide">Home
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link anchor" href="#about">About</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link anchor" href="#main_features">Features</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link anchor" href="#screenshots">Screenshots</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link anchor" href="#team">Team</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link anchor" href="#recent_news">Future works</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link anchor" href="#git_in_touch">Contact</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link anchor" href="#download_app">Download</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>
    <!-- End Header -->

    <!-- Start Header -->
    <section id="slide" class="slide background-withcolor">
        <div class="content-bottom">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-md-6" data-aos="fade-right">
                        <p class="mb-0">With us you will</p>
                        <h2>Protect yourself</h2>
                        <p>With us, protecting your privacy will never be the
                        same
                        </p>
                        <a href="#main_features" class="nav-link anchor btn btn-primary btn-white shadow btn-theme"><span>Read More</span></a>
                    </div>
                    <div class="col-md-6" data-aos="fade-left" data-aos-delay="200">
                        <img src="assets/img/mobile5-1/3.png" class="img-fluid d-block mx-auto" alt="">
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End Header -->

    <!-- Start Boxes -->
    <section id="boxes" class="boxes padding-100">
        <div class="container">
            <div class="row">
                <div class="col-md-4 col-12">
                    <div class="box" data-aos="fade-up">
                        <div class="icon">
                            <span class="lnr lnr lnr-magic-wand"></span>
                        </div>
                        <div class="space-20"></div>
                        <h4>Simple UI</h4>
                        <div class="space-15"></div>
                        <p>Our main aim is to provide our users with a smooth and clean UI so that they can learn
                        in a comfortable manner <br /><br /></p>
                    </div>
                </div>
                <div class="col-md-4 col-12">
                    <div class="box" data-aos="fade-up" data-aos-delay="200">
                        <div class="icon">
                            <span class="lnr lnr-rocket"></span>
                        </div>
                        <div class="space-20"></div>
                        <h4>Free support</h4>
                        <div class="space-15"></div>
                        <p>We have provided 24 hours 7 days support in order to make sure that our users are enjoying
                        the game in order to get the full benefit out of it</p>
                    </div>
                </div>
                <div class="col-md-4 col-12">
                    <div class="box" data-aos="fade-up" data-aos-delay="400">
                        <div class="icon">
                            <span class="lnr lnr-diamond"></span>
                        </div>
                        <div class="space-20"></div>
                        <h4>Learn Quick</h4>
                        <div class="space-15"></div>
                        <p>Using different techniques we have made the game interesting and easy so it can be taught
                        to any one with non-technical background</p>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End Boxes -->

    <!-- Start Why Us -->
    <section id="about" class="why-us padding-100 background-fullwidth background-fixed "
        style="background-image: url(assets/img/gray-bg.jpg);">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-md-6 text-center" data-aos="fade-right">
                    <img src="assets/img/mobile5-1/6.png" class="img-fluid" alt="">
                </div>
                <div class="col-md-6" data-aos="fade-zoom-in" data-aos-delay="200">
                    <h3>Providing awareness in an innovative way</h3>
                    <p>After doing some research we have figured out that a company's weakest link has always been it's
                        employee's who have no background about security, so we made sure to divide the game into different
                        parts
                    </p>
                    <div class="space-50"></div>
                    <div class="row">
                        <div class="col-lg-6 col-md-6 col-sm-6 col-12" data-aos="zoom-in" data-aos-delay="400">
                            <div class="why-us-icon">
                                <span class="lnr lnr-rocket"></span>
                                <p>We have used cutting edge technology to link the database with the application to make
                                    sure that the connection is always smooth
                                </p>
                            </div>
                            <div class="space-25"></div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-6 col-12" data-aos="zoom-in" data-aos-delay="600">
                            <div class="why-us-icon">
                                <span class="lnr lnr-rocket"></span>
                                <p>It doesn't matter where are you, you can always play and compete in the game.
                                </p>
                            </div>
                        </div>
                        <div class="space-25"></div>
                    </div>
                    <div class="row">
                        <div class="col-lg-6 col-md-6 col-sm-6 col-12" data-aos="zoom-in" data-aos-delay="800">
                            <div class="why-us-icon">
                                <span class="lnr lnr-rocket"></span>
                                <p>We made sure that everything matters, even the smallest resource!
                                </p>
                            </div>
                            <div class="space-25"></div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-6 col-12" data-aos="zoom-in" data-aos-delay="1000">
                            <div class="why-us-icon">
                                <span class="lnr lnr-rocket"></span>
                                <p>Teaching security was always a huge issue for companies we made it simple in 1,2,3!
                                </p>
                            </div>
                            <div class="space-25"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End Why Us -->

    <!-- Start Main Features -->
    <section id="main_features" class="main-features padding-100">
        <div class="container">
            <div class="row">
                <div class="text-center col-12 section-title" data-aos="fade-zoom-in">
                    <h3>Main
                        <span>Features</span>
                    </h3>
                    <div class="space-25"></div>
                    <p>Our aim is to provide awareness to people about how to be able to protect themselves from attacks
                        </p>
                    <div class="space-25"></div>
                </div>
            </div>
            <div class="row align-items-center">
                <div class="col-lg-3 text-lg-right left-side">
                    <div class="one-feature one" data-aos="fade-right" data-aos-delay="1000">
                        <h5>Attack Mode</h5>
                        <span class="lnr lnr-rocket"></span>
                        <p>Ability to scan and find users to attack and take over
                        there system.</p>
                    </div>
                    <div class="one-feature" data-aos="fade-right" data-aos-delay="1400">
                        <h5>Defence Mode</h5>
                        <span class="lnr lnr-alarm"></span>
                        <p>For more than 5 years, we’ve been passionate about achieving better.</p>
                    </div>
                    <div class="one-feature" data-aos="fade-right" data-aos-delay="1800">
                        <h5>Scenarios</h5>
                        <span class="lnr lnr-cloud"></span>
                        <p>Users go through multiple security scenario's to get
                        the feeling of how is it to be a hacker.</p>
                    </div>
                </div>
                <div class="col-lg-6 text-center">
                    <div class="features-circle">
                        <div class="circle-svg" data-aos="zoom-in" data-aos-delay="400">
                            <svg version="1.1" id="features_circle" xmlns="http://www.w3.org/2000/svg"
                                xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" width="543px"
                                height="542.953px" viewBox="0 0 543 542.953" enable-background="new 0 0 543 542.953"
                                xml:space="preserve">
                                <g>
                                    <circle fill="none" stroke="#" stroke-width="3" stroke-miterlimit="10"
                                        stroke-dasharray="11.9474,11.9474" cx="271.5" cy="271.516" r="270" />
                                    <animateTransform attributeName="transform" type="rotate" from="0" to="360"
                                        dur="50s" repeatCount="indefinite"></animateTransform>
                                </g>
                            </svg>
                        </div>
                        <img data-aos="fade-up" data-aos-delay="200" src="assets/img/tools-mobile-3.png" class="img-fluid"
                            alt="">
                    </div>
                </div>
                <div class="col-lg-3 right-side">
                    <div class="one-feature" data-aos="fade-left" data-aos-delay="1000">
                        <h5>Achievments</h5>
                        <span class="lnr lnr-construction"></span>
                        <p>Our system is a comprehensive system of applied creativity.</p>
                    </div>
                    <div class="one-feature" data-aos="fade-left" data-aos-delay="1400">
                        <h5>Phishing</h5>
                        <span class="lnr lnr-gift"></span>
                        <p>For more than 5 years, we’ve been passionate about achieving better.</p>
                    </div>
                    <div class="one-feature" data-aos="fade-left" data-aos-delay="1800">
                        <h5>Logs</h5>
                        <span class="lnr lnr-database"></span>
                        <p>For more than 5 years, we’ve been passionate about achieving better.</p>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End Main Features -->

    <!-- Start Other Features -->
    <section id="other_features" class="other-features padding-100 background-withcolor">
        <div class="container-fluid">
            <div class="row">
                <div class="text-center col-12 section-title" data-aos="fade-zoom-in">
                    <h3>Other
                        <span class="white"> Awesome </span> Features
                    </h3>
                    <div class="space-25"></div>
                    <p>Using advanced and complex mathematical algorithms we have made it possible
                    to teach people security</p>
                    <div class="space-50"></div>
                </div>
            </div>
            <div class="row align-items-center">
                <div class="col-12">
                    <div class="other-features-slider" data-aos="fade-up">

                        <div class="item text-center">
                            <span class="lnr lnr-user"></span>
                            <h4>Authentication</h4>
                            <p>Users are able to authenticate themselves in order to be able to protect themselves
                                from outsiders.
                            </p>
                        </div>
                        <div class="item text-center">
                            <span class="lnr lnr-pencil"></span>
                            <h4>Log</h4>
                            <p>Users are able to view there logs of who attacked them and when, hackers can also manipulate
                                these logs or delete them fully after performing an attack to leave 0 trace of the attack.
                            </p>
                        </div>
                        <div class="item text-center">
                            <span class="lnr lnr-cloud"></span>
                            <h4>Scan for targets</h4>
                            <p>You can scan for other users.
                            </p>
                        </div>
                        <div class="item text-center">
                            <span class="lnr lnr-construction"></span>
                            <h4>Attack list</h4>
                            <p>If you have the tools why not find a target!
                            </p>
                        </div>
                        <div class="item text-center">
                            <span class="lnr lnr-gift"></span>
                            <h4>Tools</h4>
                            <p>Tools to help you mingle around in people's lives ;)
                            </p>
                        </div>
                        <div class="item text-center">
                            <span class="lnr lnr-database"></span>
                            <h4>Bank account</h4>
                            <p>Who doesn't have a bank account these days? imagine you can get into one that is not yours.
                            </p>
                        </div>
                        <div class="item text-center">
                            <span class="lnr lnr-history"></span>
                            <h4>Mail</h4>
                            <p>Sometimes we receive the Nigerian prince email why not try it on people and see if it still
                                works.
                            </p>
                        </div>
                        <div class="item text-center">
                            <span class="lnr lnr-database"></span>
                            <h4>Scoreboard</h4>
                            <p>You think you are good? actions speak louder than words.
                            </p>
                        </div>
                        <div class="item text-center">
                            <span class="lnr lnr-gift"></span>
                            <h4>Anti Virus</h4>
                            <p>Keep using it, perhaps you might something here or there on your account ;)
                            </p>
                        </div>
                        <div class="item text-center">
                            <span class="lnr lnr-paperclip"></span>
                            <h4>Tasks</h4>
                            <p>Who doesn't like tasks and points
                            </p>
                        </div>
                        <div class="item text-center">
                            <span class="lnr lnr-gift"></span>
                            <h4>Scenarios</h4>
                            <p>We will teach you security not using only 1 method but multiple different methods.
                            </p>
                        </div>
                        <div class="item text-center">
                            <span class="lnr fa-users"></span>
                            <h4>Profile</h4>
                            <p>Show off your profile to the public
                            </p>
                        </div>
                        <div class="item text-center">
                            <span class="lnr lnr-gift"></span>
                            <h4>Crack account</h4>
                            <p>Figured out the account and the details why not try and crack the account password to finalize
                                the attack?
                            </p>
                        </div>
                        <div class="item text-center">
                            <span class="lnr lnr-gift"></span>
                            <h4>Phishing</h4>
                            <p>Not fishing. Phishing is an attack you can perform by sending out fake emails to the users
                                in order to grab there resources.
                            </p>
                        </div>
                        <div class="item text-center">
                            <span class="lnr lnr-gift"></span>
                            <h4>Bruteforce</h4>
                            <p>Try not once or twice, try a 1337 times until you get into that account.
                            </p>
                        </div>
                        <div class="item text-center">
                            <span class="lnr lnr-clock"></span>
                            <h4>Social engineering</h4>
                            <p>Know the username? If you know the person enough using our hint system try and
                                figure out the user's password.
                            </p>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End Other Features  -->

    <!-- Start Watch Video -->
    <section id="watch_video" class="watch-video padding-100">
        <div class="container-fluid">
            <div class="row">
                <div class="text-center col-12 section-title" data-aos="fade-zoom-in">
                    <h3>Watch
                        <span> Video</span>
                    </h3>
                    <div class="space-25"></div>
                    <p>See a quick review of how the application will look like.</p>
                    <div class="space-50"></div>
                </div>
                <div class="col-md-6 offset-md-3" data-aos="fade-up">
                    <div class="video" style="background-image: url('http://cybersectnt.com/assets/img/banner2.png')">
                        <img src="assets/img/mobile-4-4.png" class="img-fluid d-block mx-auto" alt="">
                        <a href="https://www.youtube.com/watch?v=1XQQStiMjAQ&feature=youtu.be" data-lity></a>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End Watch Video  -->

    <!-- Start Screenshots -->
    <section id="screenshots" class="screenshots padding-100 background-fullwidth background-fixed"
        style="background-image: url(assets/img/gray-bg.jpg);">
        <div class="container-fluid">
            <div class="row">
                <div class="text-center col-12 section-title" data-aos="fade-zoom-in">
                    <h3>Awesome
                        <span> Screenshots</span>
                    </h3>
                    <div class="space-25"></div>
                    <p>Didn't see enough from the video?
                    Check out our live screenshots directly from our phone to your screen.</p>
                    <div class="space-50"></div>
                </div>
            </div>
            <div class="row align-items-center">
                <div class="col-12">
                    <div class="screenshots-slider" data-aos="fade-up">


                        <div class="item text-center">
                            <img src="assets/img/mobile5-1/3.png" class="img-fluid d-block mx-auto" alt="">
                        </div>
                        <div class="item text-center">
                            <img src="assets/img/mobile5-1/1.png" class="img-fluid d-block mx-auto" alt="">
                        </div>
                        <div class="item text-center">
                            <img src="assets/img/mobile5-1/2.png" class="img-fluid d-block mx-auto" alt="">
                        </div>
                        <div class="item text-center">
                            <img src="assets/img/mobile5-1/4.png" class="img-fluid d-block mx-auto" alt="">
                        </div>
                        <div class="item text-center">
                            <img src="assets/img/mobile5-1/5.png" class="img-fluid d-block mx-auto" alt="">
                        </div>
                        <div class="item text-center">
                            <img src="assets/img/mobile5-1/6.png" class="img-fluid d-block mx-auto" alt="">
                        </div>
                        <div class="item text-center">
                            <img src="assets/img/mobile5-1/7.png" class="img-fluid d-block mx-auto" alt="">
                        </div>
                        <div class="item text-center">
                            <img src="assets/img/mobile5-1/8.png" class="img-fluid d-block mx-auto" alt="">
                        </div>
                        <div class="item text-center">
                            <img src="assets/img/mobile5-1/9.png" class="img-fluid d-block mx-auto" alt="">
                        </div>
                        <div class="item text-center">
                            <img src="assets/img/mobile5-1/10.png" class="img-fluid d-block mx-auto" alt="">
                        </div>
                        <div class="item text-center">
                            <img src="assets/img/mobile5-1/11.png" class="img-fluid d-block mx-auto" alt="">
                        </div>
                        <div class="item text-center">
                            <img src="assets/img/mobile5-1/12.png" class="img-fluid d-block mx-auto" alt="">
                        </div>
                        <div class="item text-center">
                            <img src="assets/img/mobile5-1/13.png" class="img-fluid d-block mx-auto" alt="">
                        </div>
                        <div class="item text-center">
                            <img src="assets/img/mobile5-1/14.png" class="img-fluid d-block mx-auto" alt="">
                        </div>
                        <div class="item text-center">
                            <img src="assets/img/mobile5-1/15.png" class="img-fluid d-block mx-auto" alt="">
                        </div>
                        <div class="item text-center">
                            <img src="assets/img/mobile5-1/16.png" class="img-fluid d-block mx-auto" alt="">
                        </div>
                        <div class="item text-center">
                            <img src="assets/img/mobile5-1/17.png" class="img-fluid d-block mx-auto" alt="">
                        </div>

                        <div class="item text-center">
                            <img src="assets/img/mobile5-1/18.png" class="img-fluid d-block mx-auto" alt="">
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End Screenshots -->

    <!-- Start Clients Testimonial -->
 
    <!-- End Clients Testimonial  -->

    <!-- Start Fun Facts -->


	<section id="facts" class="fun-facts padding-100 background-withcolor">
	   <div class="container">
		  <div class="row">
			 <div class="text-center col-12 section-title" data-aos="fade-zoom-in">
				<h3>Fun
				   <span class="white"> Facts</span>
				</h3>
				<div class="space-25"></div>
				<p>As developers we live to teach and learn, but there are some things we can't simply overlook
				   because what makes a good developer good is how much time he put into the project.
				</p>
				<div class="space-50"></div>
			 </div>
		  </div>
		  <div class="row align-items-center ">
			 <div class="col-lg-4 col-md-6 col-12">
				<div class="fact-box text-center" data-aos="fade-up" data-aos-delay="400">
				   <span class="lnr lnr-coffee-cup"></span>
				   <h5>855</h5>
				   <h6>Coffee Cups</h6>
				</div>
			 </div>
			 <div class="col-lg-4 col-md-6 col-12">
				<div class="fact-box text-center" data-aos="fade-up" data-aos-delay="800">
				   <span class="lnr lnr-code"></span>
				   <h5>8,287</h5>
				   <h6>Line Code</h6>
				</div>
			 </div>
			 <div class="col-lg-4 col-md-6 col-12">
				<div class="fact-box text-center" data-aos="fade-up" data-aos-delay="1200">
				   <span class="lnr lnr-download"></span>
				   <h5>763</h5>
				   <h6>Download</h6>
				</div>
			 </div>
		  </div>
	   </div>
	</section>


    <!-- End Fun Facts  -->


    <!-- Start Our Team -->
    <section id="team" class="our-team padding-100 background-fullwidth background-fixed"
        style="background-image: url(assets/img/gray-bg.jpg);">
        <div class="container">
            <div class="row">
                <div class="text-center col-12 section-title" data-aos="fade-zoom-in">
                    <h3>Our
                        <span> Team</span>
                    </h3>
                    <div class="space-25"></div>
                    <p>Who do you think did all that? get to know them more!</p>
                    <div class="space-50"></div>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <div class="team-slider" data-aos="fade-up">
                        <div class="item person text-center">
                            <img src="assets/img/pnooh.jpg" class="img-fluid d-block mx-auto" alt="">
                            <div class="space-20"></div>
                            <h3>Dr.Nooh Bany Muhammad</h3>
                            <div class="space-20"></div>
                            <h5>Project Manager</h5>
                            <div class="space-20"></div>
                            <p>Areas of expertise:</p>
                            <li style="font-size: 10px;">Computer Security</li>
                            <li style="font-size: 10px;">Cyber Security</li>
                            <li style="font-size: 13px;">Business analysis and process redesign</li>
                            <li style="font-size: 13px;">IT Implementations & Integration</li>
                            <li style="font-size: 13px;">Database Design</li>
                            <li style="font-size: 13px;">Software Engineering</li>
                            <br>
                            <ul>
                                <li>
                                    <a href="https://www.linkedin.com/in/nooh-bany-muhammad-a12aa2a8/" target="_Blank">
                                        <i class="fab fa-linkedin-in"></i>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="team-slider" data-aos="fade-up">
                        <div class="item person text-center">
                            <img src="assets/img/hameed.jpg" class="img-fluid d-block mx-auto" alt="">
                            <div class="space-20"></div>
                            <h3>AbdulHameed Salama</h3>
                            <div class="space-20"></div>
                            <h5>Website Developer</h5>
                            <div class="space-20"></div>
                            <p>Website development & Application testing
                            </p>
                            <br>
                            <ul>
                                <li>
                                    <a href="#">
                                        <i class="fab fa-facebook-f"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i class="fab fa-twitter"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i class="fab fa-linkedin-in"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i class="fab fa-instagram"></i>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div class="item person text-center">
                            <img src="assets/img/aziz.png" class="img-fluid d-block mx-auto" alt="">
                            <div class="space-20"></div>
                            <h3>AbdulAziz Karam</h3>
                            <div class="space-20"></div>
                            <h5>Application Developer</h5>
                            <div class="space-20"></div>
                            <p>UI Design / Integration / Android development / Database / Testing
                            </p>
                            <ul>
                                <li>
                                    <a href="#">
                                        <i class="fab fa-facebook-f"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="https://twitter.com/azikar24">
                                        <i class="fab fa-twitter"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="www.linkedin.com/in/abdulaziz-karam-02b78b22">
                                        <i class="fab fa-linkedin-in"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="https://www.instagram.com/azikar24/">
                                        <i class="fab fa-instagram"></i>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div class="item person text-center">
                            <img src="assets/img/anas.png" class="img-fluid d-block mx-auto" alt="">
                            <div class="space-20"></div>
                            <h3>Anas El-Ghabra</h3>
                            <div class="space-20"></div>
                            <h5>Application Developer</h5>
                            <div class="space-20"></div>
                            <p>Video editing / Android development / Testing / Database
                            </p>
                            <ul>
                                <li>
                                    <a href="#">
                                        <i class="fab fa-facebook-f"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i class="fab fa-twitter"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i class="fab fa-linkedin-in"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i class="fab fa-instagram"></i>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div class="item person text-center">
                            <img src="assets/img/hamood.png" class="img-fluid d-block mx-auto" alt="">
                            <div class="space-20"></div>
                            <h3>Mohammad Masoud</h3>
                            <div class="space-20"></div>
                            <h5>Application Developer</h5>
                            <div class="space-20"></div>
                            <p>UI Design / Android Development / Testing
                            </p>
                            <br>
                            <ul>
                                <li>
                                    <a href="#">
                                        <i class="fab fa-facebook-f"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i class="fab fa-twitter"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i class="fab fa-linkedin-in"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i class="fab fa-instagram"></i>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End Our Team -->

    <!-- Start FAQ -->
    <section id="faq" class="faq padding-100">
        <div class="container">
            <div class="row">
                <div class="text-center col-12 section-title" data-aos="fade-zoom-in">
                    <h3>Common
                        <span> FAQ</span>
                    </h3>
                    <div class="space-25"></div>
                    <p>Here are the most asked questions from our point of view</p>
                    <div class="space-50"></div>
                </div>
            </div>
            <div class="row align-items-center">
                <!--align-items-center-->
                <div class="col-md-8 col-12" data-aos="fade-right">
                    <div class="accordion" id="faqAccordion">
                        <div class="card shadow">
                            <div class="card-header" id="heading_1">
                                <h5 class="mb-0">
                                    <button class="btn btn-link" type="button" data-toggle="collapse"
                                        data-target="#collapse_1" aria-expanded="true" aria-controls="collapse_1">
                                        What is CybersecTNT
                                    </button>
                                </h5>
                            </div>

                            <div id="collapse_1" class="collapse show" aria-labelledby="heading_1"
                                data-parent="#faqAccordion">
                                <div class="card-body">
                                    CyberSecTNT is a game with the aim of helping non-technical users get a general
                                    idea of how the security in a digital environment can be easily breached. Since the
                                    application is aimed at non-technical users it will not be complicated and will give
                                    the users a general idea of how breaches happen from both sides, attacking and
                                    defending. With how the internet is getting more integrated with our lives by the
                                    day it is crucial for people to get educated on how to keep their data safe and that
                                    is the aim of our application.
                                </div>
                            </div>
                        </div>
                        <div class="card shadow">
                            <div class="card-header" id="heading_2">
                                <h5 class="mb-0">
                                    <button class="btn btn-link collapsed" type="button" data-toggle="collapse"
                                        data-target="#collapse_2" aria-expanded="false" aria-controls="collapse_2">
                                        How To Use The Application
                                    </button>
                                </h5>
                            </div>
                            <div id="collapse_2" class="collapse" aria-labelledby="heading_2"
                                data-parent="#faqAccordion">
                                <div class="card-body">
                                    Just like any online game as soon as the application is launched the user is asked
                                    to either log in or make an account. After account set up is done, they are free to
                                    play the game. The user starts off by scanning possible targets and chooses to attack
                                    someone through the different attack options available. They can either pass or fail
                                    the attack depending on the defender’s tools levels and the attacker’s tools levels.
                                    Tools can be upgraded in the store with coins gained from finishing tasks or
                                    transferring funds from another user’s bank account after a cyber-attack.
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 col-12" data-aos="fade-left" data-aos-delay="400">
                    <img src="assets/img/mobile5-1/3.png" class="img-fluid b-block mx-auto" alt="">
                </div>
            </div>
        </div>
    </section>
    <!-- End FAQ  -->


    <!-- Start Recent News -->
    <section id="recent_news" class="recent-news padding-100">
        <div class="container">
            <div class="row">
                <div class="text-center col-12 section-title" data-aos="fade-zoom-in">
                    <h3>Future
                        <span>Works</span>
                    </h3>
                    <div class="space-25"></div>
                    <p>We will never stop at this, we are always looking for advancements and improvements, here is a sneak
                    peak to what we have for you in the future!</p>
                    <div class="space-50"></div>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <div class="recent-news-slider" data-aos="fade-up">
                        <div class="item">
                            <div class="row align-items-center">
                                <div class="col-md-6 col-12">
                                    <img src="assets/img/banner.png" class="img-fluid" alt="">
                                </div>
                                <div class="col-md-6 col-12">
                                    <h3>A sneak peak to our upcoming updates</h3>
                                    <div class="space-15"></div>
                                    <p>For future works we are planning to add teams/groups into the game were players
										will be able to play together as a team to become a threat or eliminate a threat.
										Also, we are planning to add animations into the application and making it more
										colorful in general. We are also going to be contacting companies and working
										with them to make the application fit the companies’ style, for example, their
										database and so on. Furthermore, we want to make our application available for
										IOS devices. Moreover, we are looking forward to adding an offline mode were
										users will be playing against “bots” with different difficulties.
										Finally, we will want to implement machine learning into our application.
                                    </p>
                                    <div class="space-15"></div>
                                    <div class="space-15"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </section>
    <!-- End Recent News  -->

    <!-- Start Download App -->
    <section id="download_app" class="download-app padding-100 pb-0 background-fullwidth background-fixed"
        style="background-image: url(assets/img/gray-bg.jpg);">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-6 col-12" data-aos="fade-right">
                    <h2>Download our free trial App</h2>
                    <p>Our application is available for the public on the Play store and soon to be on the Apple store.
                    </p>
                    <a href="https://play.google.com/store/apps/details?id=com.cybersectnt.cybersectntdemo1&hl=en"
                       class="btn btn-primary shadow  btn-colord btn-theme" tabindex="0">
                        <i class="fab fa-google-play"></i>
                        <span>Git it on
                            <br>GOOGLE PLAY</span>
                    </a>
                </div>
                <div class="col-lg-6 col-12" data-aos="fade-left" data-aos-delay="400">
                    <img src="assets/img/splash-mobile-6.png" class="img-fluid d-block mx-auto" alt="">
                </div>
            </div>
        </div>
    </section>
    <!-- End Download App -->

    <!-- Start  Git in touch -->
    <section id="git_in_touch" class="git-in-touch padding-100">
        <div class="container">
            <div class="row">
                <div class="text-center col-12 section-title" data-aos="fade-zoom-in">
                    <h3>Git
                        <span> in </span>touch
                    </h3>
                    <div class="space-25"></div>
                    <p>Whether you have a question or want to Git to know us, shoot us a message using our official form
                    if you are too lazy to email us.</p>
                    <div class="space-50"></div>
                </div>
            </div>
            <form action="" id="form-box" method="POST" data-aos="fade-up">
                <div class="row">
                    <div class="col-md-4">
                        <div class="form-group">
                            <input type="text" name="name" class="form-control" placeholder="Enter Your Name" required>
                            <span class="focus-border"></span>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <input type="email" name="email" class="form-control" placeholder="Enter Your Email" required>
                            <span class="focus-border"></span>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <input type="text" name="sub" class="form-control" placeholder="Enter Your Subject" required>
                            <span class="focus-border"></span>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-group">
                            <textarea class="form-control" name="message" id="msg-box" rows="4" placeholder="Enter Your Message" required></textarea>
                            <span class="focus-border"></span>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="space-25"></div>
                        <button type="submit" name="submit" class="btn btn-primary shadow btn-colord btn-theme"><span>Send
                                Message</span></button>
                    </div>
                </div>
            </form>
            <div class="space-50"></div>
            <div class="row contact-info">
                <div class="col-md-4 col-12 text-center">
                    <div class="info-box" data-aos="fade-right" data-aos-delay="400">
                        <span class="lnr lnr-map-marker"></span>
                        <h5>15 Salem Al Mubarak St, Salmiya, Kuwait</h5>
                    </div>
                </div>
                <div class="col-md-4 col-12 text-center">
                    <div class="info-box" data-aos="fade-up" data-aos-delay="800">
                        <span class="lnr lnr-phone"></span>
                        <h5>+965 2224 8399</h5>
                    </div>
                </div>
                <div class="col-md-4 col-12 text-center">
                    <div class="info-box" data-aos="fade-left" data-aos-delay="1200">
                        <span class="lnr lnr-envelope"></span>
                        <a href="mailto:info@cybersectnt.com">info@cybersectnt.com</a>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End  Git in touch  -->

    <!-- Start  Map -->
    <section class="map">
        <iframe
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3477.8859388944643!2d48.081144650885285!3d29.344338358170496!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3fcf7626163f442d%3A0x1a332c240e88c341!2sAmerican%20University%20of%20Kuwait!5e0!3m2!1sen!2seg!4v1589892552654!5m2!1sen!2seg"
            style="border:0" allowfullscreen></iframe>
    </section>
    <!-- End  Map  -->

    <!-- Start  Footer -->
    <footer class="padding-100 pb-0">
        <div class="space-50"></div>
        <div class="footer-widgets">
            <div class="container">
                <div class="row">
                    <div class="col-lg-3 col-md-6 col-12">
                        <div class="widget">
                            <img src="assets/img/Webp.net-resizeimage.gif" class="img-fluid" alt="">
                            <p>CyberSecTNT is a game with the aim of helping non-technical users get a general
                                idea of how the security in a digital environment can be easily breached.
                            </p>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6 col-12">
                        <div class="widget">
                            <h6>Quick Links</h6>
                            <ul>
                                <li class="nav-item">
                            <a class="nav-link anchor active" href="#slide">Home
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link anchor" href="#about">About</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link anchor" href="#main_features">Features</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link anchor" href="#screenshots">Screenshots</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link anchor" href="#team">Team</a>
                        </li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6 col-12">
                        <div class="widget">
                            <h6>Social Media</h6>
                            <ul>
                                <li>
                                    <a href="#">Facbook</a>
                                </li>
                                <li>
                                    <a href="#">Instagram</a>
                                </li>
                                <li>
                                    <a href="#">LinkedIn</a>
                                </li>
                                <li>
                                    <a href="#">Twitter</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6 col-12">
                        <div class="widget">
                            <h6>Quick Contact</h6>
                            <ul>
                                <li>
                                    <span>Phone : </span>+965 2224 8399
                                </li>
                                <li>
                                    <span>Email : </span>
                                    <a href="mailto:info@cybersectnt.com"></a>info@cybersectnt.com</li>
                                <li>
                                    <span>Address : </span>15 Salem Al Mubarak St, Salmiya, Kuwait</li>

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="space-50"></div>
        <div class="copyright">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-md-4">
                        <p>All rights reserved © <a href="#http://cybersectnt.com">CyberSecTNT</a>, 2020</p>
                    </div>
                    <div class="offset-md-4 col-md-4">
                        <ul class="nav justify-content-end">
                            <li class="nav-item">
                                <a class="nav-link" href="#">Terms and Conditions</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">Privacy Policy</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </footer>
    <!-- End  Footer  -->

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="assets/js/jquery-3.3.1.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <!-- Bootstrap JS -->
    <script src="assets/js/popper.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>

    <!-- svg -->
    <script src="https://cdn.linearicons.com/free/1.0.0/svgembedder.min.js"></script>

    <!-- Slick Slider JS -->
    <script src="assets/js/slick.min.js"></script>

    <!-- Counterup JS -->
    <script src="assets/js/waypoints.min.js"></script>
    <script src="assets/js/jquery.counterup.js"></script>

    <!-- AOS JS -->
    <script src="assets/js/aos.js"></script>

    <!-- lity JS -->
    <script src="assets/js/lity.min.js"></script>

    <!-- Our Main JS -->
    <script src="assets/js/main.js"></script>

</body>

</html>
