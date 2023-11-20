use std::io::{Read, Write};
use std::net::TcpStream;

pub fn run_client() {
    let mut stream = TcpStream::connect("127.0.0.1:9090").expect("Failed to connect to server");

    // Request the server to run the test function
    let request = "run_test";
    stream.write_all(request.as_bytes()).unwrap();

    // Receive the result from the server
    let mut buffer = [0; 1024];
    let bytes_read = stream.read(&mut buffer).expect("Failed to read from server");
    let memory_info = String::from_utf8_lossy(&buffer[..bytes_read]);
    //stream.shutdown(std::net::Shutdown::Both).expect("shutdown call failed");
    println!("Received memory information from server:\n{}", memory_info);
}

fn main() {
    run_client();
    
}