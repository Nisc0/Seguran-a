package client;

import domain.IPCheck;
import domain.Photo;
import domain.PhotoData;
import domain.PhotoOpinion;
import exceptions.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class PhotoShare {

    public static void main(String[] args) throws NotValidAddressException, IOException {

        String currUser = args[1];

        if (args.length != 4) {
            System.out.println("Por favor siga a forma: <localUserId> <password> <serverAddress>");
            return;
        }

        String[] serverPort;
        String serverAdd = args[3];
        int port;
        if (IPCheck.isIPValid(serverAdd)) {
            serverPort = serverAdd.split(":");
            port = Integer.parseInt(serverPort[1]);
        } else {
            throw new NotValidAddressException();
        }

        ClientMessage cm = new ClientMessage(serverPort[0], port);
        //TODO: verificar exception do clientmessage

        boolean isSessionOpen = false;

        try {
            isSessionOpen = cm.startSession(currUser, args[2]);
        } catch (WrongUserPasswordException | NotFollowingException | NoSuchUserException e) {
            System.out.println("Something went wrong! " + e.getMessage());
        } catch (ClassNotFoundException e2) {
            e2.getStackTrace();
        } catch (NoSuchPhotoException | AlreadyFollowingException | DuplicatePhotoException |
                AlreadyLikedException | AlreadyDislikedException | AlreadyNotFollowingException e1) {
            System.out.println("That didn't work! " + e1.getMessage());
        }

        while (isSessionOpen) {
            Scanner sc = new Scanner(System.in);
            String line = sc.nextLine();
            String[] in = line.split(" ");
            try {
                if (in.length > 0) {
                    if (in[0].equals("quit")) {
                        boolean toCloseSession = cm.endSession(currUser);
                        if (toCloseSession) {
                            isSessionOpen = false;
                        }
                    }
                    char op = in[0].charAt(1);

                    switch (op) {
                        case 'a':
                            if (in.length > 1) {
                                int i;
                                for (i = 1; i < in.length; i++) {
                                    BufferedImage img = null;
                                    String photoID = null;
                                    try {
                                        File imgFile = new File(in[i]);
                                        img = ImageIO.read(imgFile);
                                        photoID = imgFile.getName();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    Photo ph = new Photo(photoID);

                                    if (cm.addPhoto(currUser, photoID, ph, img)) {
                                        System.out.println("You've added given photo!");
                                    } else
                                        continue;
                                }
                            } else {
                                System.out.println("Please, call it like this: -a " +
                                        "<photos>");
                                return;
                            }
                            break;
                        case 'l':
                            if (in.length == 2) {
                                Iterable<PhotoData> list = cm.getAllPhotosData(currUser, in[1]);
                                for (PhotoData pd : list) {
                                    System.out.println(pd.toString());
                                }
                            } else {
                                System.out.println("Please, call it like this: -l " +
                                        "<userID>");
                                return;
                            }
                            break;
                        case 'i':
                            if (in.length == 3) {
                                PhotoOpinion opi = cm.getPhotoOpinion(currUser, in[1], in[2]);
                                System.out.println(opi.toString());
                            } else {
                                System.out.println("Please, call it like this: -i " +
                                        "<userID> <photo>");
                                return;
                            }
                            break;
                        case 'g':
                            if (in.length == 2) {
                                Iterable<Photo> list = cm.getAllPhotos(currUser, in[1]);
                                for (Photo pd : list) {
                                    System.out.println(pd.toString());
                                }
                            } else {
                                System.out.println("Please, call it like this: -g " +
                                        "<userID>");
                                return;
                            }
                            break;
                        case 'c':
                            if (in.length == 4) {
                                String[] comments = line.split("\"");
                                if (cm.commentPhoto(currUser, comments[1], in[in.length - 2], in[in.length - 1]))
                                    System.out.println("You've commented given photo!");
                            } else {
                                System.out.println("Please, call it like this: -c " +
                                        "<comment> <userID> <photo>");
                                return;
                            }
                            break;
                        case 'L':
                            if (in.length == 3) {
                                if (cm.likePhoto(currUser, in[1], in[2]))
                                    System.out.println("You're now liking given photo!");
                            } else {
                                System.out.println("Please, call it like this: -L " +
                                        "<userID> <photo>");
                                return;
                            }
                            break;
                        case 'D':
                            if (in.length == 3) {
                                if (cm.dislikePhoto(currUser, in[1], in[2]))
                                    System.out.println("You're now disliking given photo!");
                            } else {
                                System.out.println("Please, call it like this: -D " +
                                        "<userID> <photo>");
                                return;
                            }
                            break;
                        case 'f':
                            if (in.length > 1) {
                                int i;
                                for (i = 1; i < in.length; i++) {
                                    if (cm.followUser(currUser, in[i]))
                                        System.out.println("You're now following given username!");
                                    else
                                        continue;
                                }
                            } else {
                                System.out.println("Please, call it like this: -f " +
                                        "<followUserIds>");
                                return;
                            }
                            break;
                        case 'r':
                            if (in.length > 1) {
                                int i;
                                for (i = 1; i < in.length; i++) {
                                    if (cm.unfollowUser(currUser, in[i]))
                                        System.out.println("You're now unfollowing given username!");
                                    else
                                        continue;
                                }
                            } else {
                                System.out.println("Please, call it like this: -r " +
                                        "<followUserIds>");
                                return;
                            }
                            break;
                    }
                }
            } catch (WrongUserPasswordException | NotFollowingException | NoSuchUserException e) {
                System.out.println("Something went wrong! " + e.getMessage());
            } catch (ClassNotFoundException e2) {
                e2.getStackTrace();
            } catch (NoSuchPhotoException | AlreadyFollowingException | DuplicatePhotoException |
                    AlreadyLikedException | AlreadyDislikedException | AlreadyNotFollowingException e1) {
                System.out.println("That didn't work! " + e1.getMessage());
            }
        }
    }
}