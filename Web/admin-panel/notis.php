<!DOCTYPE html>
<html lang="en">

<?php
    include("header.php");
?>

  <!-- Page Wrapper -->
  <div id="wrapper">

    <!-- Sidebar -->
      <?php
        include ("sidebar.php");
      ?>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">


        <!-- Begin Page Content -->
        <div class="container-fluid">

            <br />
          <!-- Page Heading -->
          <h1 class="h3 mb-2 text-gray-800">User Tables</h1>
          <p class="mb-4"> <a target="_blank" href="https://datatables.net"></a>.</p>

            <?php
                echo "Sent";
                if($_SERVER['REQUEST_METHOD'] == "POST" and isset($_POST['bodyText']))
                {
                    $bodyValue = $_POST['bodyText'];
                    $bodyValue  = "{
                        \"to\" : \"/topics/all\",
                        \"collapse_key\" : \"type_a\",
                        \"notification\" : {
                            \"body\" : \" $bodyValue \",
                            \"title\": \"CyberSecTNT\"
                        }
                       }"; 
                        
                    $handle = curl_init();
                    $url = 'https://fcm.googleapis.com/fcm/send?';
                    curl_setopt($handle, CURLOPT_URL, $url);
                    curl_setopt($handle, CURLOPT_RETURNTRANSFER, true);
                    curl_setopt($handle, CURLOPT_HTTPHEADER,     array('Content-Type: application/json', 'Authorization: key=AAAAut4BldE:APA91bGVCSmD4pNJuXE5xjsKA7QBle_riarqTwu2Cc57z_9ItoEAYkDXtbVUDnh07OpwTRQao2B06ioJEyw4lqHjHnv9mqsTj7ZnpY5VBiWWhvsXYoOgh6oQhgixA1_d3lq8xabJwvC6'));
                    curl_setopt($handle, CURLOPT_POST,           1 );
                    curl_setopt($handle, CURLOPT_POSTFIELDS,      $bodyValue   );
                    
                    $data = curl_exec($handle);
                    curl_close($handle);
                }
                 
            
                
            ?>
          <!-- DataTales Example -->
            <div class="card-body">
              <div class="table-responsive">
                  <div class="card shadow mb-4">
                      <div class="card-header py-3">
                          <h6 class="m-0 font-weight-bold text-primary">Send notification</h6>
                      </div>
                      <div class="card-body">
                          <div class="table-responsive">
                              <form class="notification" method="post" action="notis.php">
                                  <div class="form-group">
                                      <input type="text" name="bodyText" class="form-control form-control-user"
                                             id="notiText text" aria-describedby="emailHelp"
                                             placeholder="Place the notification text here ...">
                                  </div>
                                  <input type="submit" name="NotificationButton" id="btnSendNotification" value="Send notification" class="btn btn-primary btn-user btn-block"/>
                                    
                              </form>
                          </div>
                      </div>
                  </div>
              </div>
            </div>

        </div>
        <!-- /.container-fluid -->

      </div>
      <!-- End of Main Content -->

      <!-- Footer -->
        <?php
            include ("footer.php");
        ?>
      <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
        <div class="modal-footer">
          <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
          <a class="btn btn-primary" href="login.php">Logout</a>
        </div>
      </div>
    </div>
  </div>

  <!-- Bootstrap core JavaScript-->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="js/sb-admin-2.min.js"></script>

  <!-- Page level plugins -->
  <script src="vendor/datatables/jquery.dataTables.min.js"></script>
  <script src="vendor/datatables/dataTables.bootstrap4.min.js"></script>

  <!-- Page level custom scripts -->
  <script src="js/demo/datatables-demo.js"></script>

</body>

</html>
