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
          <h1 class="h3 mb-2 text-gray-800">Phishing</h1>
          <p class="mb-4"> <a target="_blank" href="https://datatables.net"></a>.</p>

     <!--       <div class="d-sm-flex align-items-center justify-content-between mb-4">
                <a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i class="fas fa-user fa-sm text-white-50"></i> Add new user</a>
            </div>-->

          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Phishing emails</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                  <iframe src="https://cybers.retool.com/embedded/public/9751c68b-375c-4506-aca0-32509cc7a2e8" width="1350" height="1350">
                      <p>Your browser does not support iframes.</p>
                  </iframe>
<!--                    <tr>
                        <th>Name</th>
                        <th>E-Mail</th>
                        <th>User Level</th>
                        <th>Actions</th>
                    </tr>
                  </thead>
                  <tfoot>
                    <tr>
                        <th>Name</th>
                        <th>E-Mail</th>
                        <th>User Level</th>
                        <th>Actions</th>
                    </tr>
                  </tfoot>
                  <tbody>
                    <tr>
                        <td>Tiger Nixon</td>
                        <td>System Architect</td>
                        <td>Edinburgh</td>
                        <td>
                            <a href="" class="btn btn-primary btn-sm"> Edit</a>
                            <a href="" class="btn btn-danger btn-sm"> Delete</a>
                        </td>
                    </tr>
                  </tbody>-->
                </table>
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
