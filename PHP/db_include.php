    <?php $conn = mysqli_connect('localhost', 'Tester2', 'Tester123!', 'studentforum'); /* 
check connection */ if (!$conn) {
    printf("Connect failed: %s\n", mysqli_connect_error()); exit();
}
?>
