package com.example.cryptor.eliptic;

import com.example.cryptor.helpful.PrimeGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.math.BigInteger;

public class ECCKeyExchange {
    private static final Logger logger = LogManager.getLogger(ECCKeyExchange.class);
    private static final int A = -3;
    private static final int SECURITY_BITS = 128;
    EllipticCurve curve;

    public BigInteger serverStart(PrintWriter out, ObjectOutputStream objectOutputStream, BufferedReader in, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        // NIST BASE VALUES FOR G POINT
        BigInteger xG = new BigInteger("48439561293906451759052585252797914202762949526041747995844080717082404635286");
        BigInteger yG = new BigInteger("36134250956749795798585127919587881956611106672985015071877198253568414405109");

        // Define elliptic curve parameters (a, b, p)
        BigInteger a = BigInteger.valueOf(A);
        BigInteger b = PrimeGenerator.generatePrimeNumber(SECURITY_BITS);
        BigInteger p = PrimeGenerator.generatePrimeNumber(SECURITY_BITS);

        curve = new EllipticCurve(a, b, p);
        objectOutputStream.writeObject(curve);
        logger.info("Sending NIST 256 version of curve to client");
        logger.info(curve.toString());

        // Generate a base point on the curve (P)
        Point P = new Point(xG, yG);
        objectOutputStream.writeObject(P);
        logger.info("Sending G Base Point to client");
        logger.info(P.toString());


        BigInteger n = PrimeGenerator.generatePrimeNumber(SECURITY_BITS);
        System.out.println("This N is private information, no one has it: " + n);

        Point nP = curve.multiply(P, n);
        objectOutputStream.writeObject(nP);
        objectOutputStream.flush();
        logger.info("Sending N Point to client so he can multiply point and get shared key");
        logger.info(nP.toString());

        Point mP = (Point) objectInputStream.readObject();
        // Both parties compute the shared key
        Point sharedKeyOnServer = curve.multiply(mP, n);
        System.out.println("This is shared private key, only client has it too: " + sharedKeyOnServer);
        return sharedKeyOnServer.getX().multiply(sharedKeyOnServer.getY());

//        // Verify that both shared keys are equal (they should be)
//        if (sharedKeyOnServer.getX().equals(sharedKeyAlice.getX()) && sharedKeyOnServer.getY().equals(sharedKeyAlice.getY())) {
//            System.out.println("Shared Key Alice: " + sharedKeyAlice.getX() + ", " + sharedKeyAlice.getY());
//            System.out.println("Shared Key Bob: " + sharedKeyOnServer.getX() + ", " + sharedKeyOnServer.getY());
//
//        } else {
//            System.out.println("Error: Shared keys do not match.");
//        }
    }

//    public void start() {
//        int securityBits = 128;
//
//        // NIST BASE VALUES FOR G POINT
//        BigInteger xG = new BigInteger("48439561293906451759052585252797914202762949526041747995844080717082404635286");
//        BigInteger yG = new BigInteger("36134250956749795798585127919587881956611106672985015071877198253568414405109");
//
//        // Define elliptic curve parameters (a, b, p)
//        BigInteger a = BigInteger.valueOf(-3);
//        BigInteger b = PrimeGenerator.generatePrimeNumber(securityBits);
//        BigInteger p = PrimeGenerator.generatePrimeNumber(securityBits);
//
//        curve = new EllipticCurve(a, b, p);
//
//        // Generate a base point on the curve (P)
//        Point P = new Point(xG, yG);
//
//        // Bob computes nP (where n is a random secret)
//        BigInteger n = PrimeGenerator.generatePrimeNumber(securityBits);
//        Point nP = curve.multiply(P, n);
//
//        // Bob sends curve parameters, P, and nP to Alice
//
//        // Alice generates her own random secret (m)
//        BigInteger m = PrimeGenerator.generatePrimeNumber(securityBits);
//
//        // Alice computes mP
//        Point mP = curve.multiply(P, m);
//
//        // Alice sends mP to Bob
//
//        // Both parties compute the shared key
//        Point sharedKeyBob = curve.multiply(nP, m);
//        Point sharedKeyAlice = curve.multiply(mP, n);
//
//        // Verify that both shared keys are equal (they should be)
//        if (sharedKeyBob.getX().equals(sharedKeyAlice.getX()) && sharedKeyBob.getY().equals(sharedKeyAlice.getY())) {
//            System.out.println("Shared Key Alice: " + sharedKeyAlice.getX() + ", " + sharedKeyAlice.getY());
//            System.out.println("Shared Key Bob: " + sharedKeyBob.getX() + ", " + sharedKeyBob.getY());
//
//        } else {
//            System.out.println("Error: Shared keys do not match.");
//        }
//    }
}
