/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grpc.service;

import com.proto.greet.GreetManyTimesRequest;
import com.proto.greet.GreetManyTimesResponse;
import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;

import io.grpc.stub.StreamObserver;

/**
 *
 * @author Eder_Crespo
 */
public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {

    // streaming server method
    @Override
    public void greetManyTimes(GreetManyTimesRequest request, StreamObserver<GreetManyTimesResponse> responseObserver) {

        try {

            final String firstName = request.getGreeting().getFirstName();

            for (int i = 0; i < 10; i++) {

                String result = "Hello ".concat(firstName).concat(" response number").concat(String.valueOf(i));

                final GreetManyTimesResponse response = GreetManyTimesResponse
                        .newBuilder()
                        .setResult(result)
                        .build();

                responseObserver.onNext(response);

                Thread.sleep(1000L);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            responseObserver.onCompleted();
        }
    }

    // unary server method
    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {

        final Greeting greeting = request.getGreeting();

        final String firstName = greeting.getFirstName();

        final String result = "Hello ".concat(firstName);

        final GreetResponse reponse = GreetResponse.newBuilder()
                .setResult(result)
                .build();

        // send the response
        responseObserver.onNext(reponse);

        // complete the RPC call
        responseObserver.onCompleted();
    }

}
