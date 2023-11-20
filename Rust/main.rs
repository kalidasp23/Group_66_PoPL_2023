mod server;
mod client;

use std::thread;

fn main() {
    // Start the server
    

    // Simulate multiple clients
    let num_clients = 500; // Change this value to the number of clients you want
    //server::start_server(num_clients);

    let mut handles = vec![];

    for _ in 0..num_clients {
        let handle = thread::spawn(|| {
            client::run_client();
        });
        handles.push(handle);
    }

    for handle in handles {
        handle.join().unwrap();
    }

    
}

