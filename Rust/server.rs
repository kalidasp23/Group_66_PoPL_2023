/* 

use std::{
    fs,
    io::{prelude::*, BufReader},
    net::{TcpListener, TcpStream},
    thread,
    time::Duration,
};

fn main() {
    let listener = TcpListener::bind("127.0.0.1:7878").unwrap();

    for stream in listener.incoming() {
        let stream = stream.unwrap();

        handle_connection(stream);
    }
}

fn handle_connection(mut stream: TcpStream) {
    let buf_reader = BufReader::new(&mut stream);
    let request_line = buf_reader.lines().next().unwrap().unwrap();

    if request_line == "GET / HTTP/1.1" {
        let status_line = "HTTP/1.1 200 OK";
        let contents = fs::read_to_string("hello.html").unwrap();
        let length = contents.len();

        let response = format!(
            "{status_line}\r\nContent-Length: {length}\r\n\r\n{contents}"
        );

        stream.write_all(response.as_bytes()).unwrap();
    } else {
        let status_line = "HTTP/1.1 404 NOT FOUND";
        let contents = fs::read_to_string("404.html").unwrap();
        let length = contents.len();

        let response = format!(
            "{status_line}\r\nContent-Length: {length}\r\n\r\n{contents}"
        );

        stream.write_all(response.as_bytes()).unwrap();
    }
} */

use std::io::{Read, Write};
use std::net::{TcpListener, TcpStream};
use std::thread;
use sysinfo::{System, RefreshKind};
use sysinfo::SystemExt;
use std::time::Instant;


fn get_memory_info() -> String {
    let mut system = System::new_all();
    system.refresh_all();

    let memory = system.total_memory();
    let free_memory = system.free_memory();

    format!(
        "Free Memory: {} KB\nTotal Memory: {} KB",
        free_memory / 1024,
        memory / 1024
    )
}

pub fn handle_client(mut stream: TcpStream) {
    let mut system = System::new_with_specifics(RefreshKind::new().with_memory());

    // Simulating a workload (1*1) a hundred times
    let start_time = std::time::Instant::now();
    //for _ in 0..100000000 {
    //    let _result = 1 * 1; // Simulating a simple computation
    //}
    

    // Gather system information
    let memory_info = get_memory_info();
    let elapsed_time = start_time.elapsed().as_millis();

    let response = format!(
        "Response time: {} ms\n{}",
        elapsed_time,
        memory_info
    );


    // Send the response to the client
    stream
        .write_all(response.as_bytes())
        .expect("Failed to send response");
}

pub fn start_server() {
    let listener = TcpListener::bind("127.0.0.1:9090").expect("Failed to bind");
    println!("Server listening on port 9090...");

    for stream in listener.incoming() {
        match stream {
            Ok(stream) => {
                // Handle the client in a separate thread
                thread::spawn(move || {
                    handle_client(stream);
                });
            }
            Err(e) => {
                println!("Failed to establish a connection: {}", e);
            }
        }
    }
}

fn main() {
    start_server();
}
