/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grpc.client;

//import grpc.DummyServiceGrpc;
import com.proto.greet.GreetManyTimesRequest;
import com.proto.greet.GreetManyTimesResponse;
import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;

/**
 *
 * @author Eder_Crespo
 */
public class GreetingClient extends ClientChannelService {

    public static void main(String[] args) {
        new GreetingClient();
    }

    @Override
    protected void performRequest(ManagedChannel channel) {
        performStreamOperation(channel);
    }

    private void performStreamOperation(ManagedChannel channel) {

        final Greeting greeting = Greeting.newBuilder()
                .setFirstName("Bruce")
                .setSecondName("Lee")
                .build();

        final GreetManyTimesRequest request = GreetManyTimesRequest.newBuilder()
                .setGreeting(greeting)
                .build();

        final GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        // each response is received in a block manner
        greetClient.greetManyTimes(request).forEachRemaining(response -> {
            System.out.println(response.getResult());
        });
    }

    private void performUnaryOperation(ManagedChannel channel) {

        System.out.println("I am a client");

        System.out.println("Creating a stub");
        // sync call
        final GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        final Greeting greeting = Greeting.newBuilder()
                .setFirstName("Jonny")
                .setSecondName("Walker")
                .build();

        final GreetRequest request = GreetRequest.newBuilder()
                .setGreeting(greeting)
                .build();

        final GreetResponse response = greetClient.greet(request);

        System.out.println(response.getResult());
    }

}
