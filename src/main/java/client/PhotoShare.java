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

	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws NotValidAddressException, IOException {

		String currUser = args[0];
		if (args.length != 3) {
			System.out.println("Please, call it like this: <localUserId> <password> <serverAddress>");
			return;
		}

		System.setProperty("javax.net.ssl.keyStore", "./Autentication/ClientKeyStore.keyStore");
		System.setProperty("javax.net.ssl.keyStorePassword", "dibrunis");
		System.setProperty("javax.net.ssl.trustStore", "./Autentication/ClientTrustStore.keyStore");

		String[] serverPort;
		String serverAdd = args[2];
		int port;
		if (IPCheck.isIPValid(serverAdd)) {
			serverPort = serverAdd.split(":");
			port = Integer.parseInt(serverPort[1]);
		} else
			throw new NotValidAddressException();

		ClientMessage cm = new ClientMessage(serverPort[0], port);

		boolean isSessionOpen = false;

		try {
			System.out.println("Let's begin!");
			isSessionOpen = cm.startSession(currUser, args[1]);
		} catch (WrongUserPasswordException | NotFollowingException | NoSuchUserException e) {
			System.out.println("Something went wrong! " + e.getMessage());
		} catch (ClassNotFoundException e2) {
			e2.getStackTrace();
		} catch (NoSuchPhotoException | AlreadyFollowingException | DuplicatePhotoException | AlreadyLikedException
				| AlreadyDislikedException | AlreadyNotFollowingException e1) {
			System.out.println("That didn't work! " + e1.getMessage());
		}

		System.out.println("Awaiting orders:");
		while (isSessionOpen) {
			System.out.print("> ");

			String line = scan.nextLine();
			String[] in = line.split(" ");

			try {
				if (in.length > 0) {
					if (in[0].equals("quit")) {
						boolean toCloseSession = cm.endSession(currUser);
						if (toCloseSession) {
							isSessionOpen = false;
							break;
						}
					}

					if (in[0].length() < 2 || in[0].charAt(0) != '-') {
						System.out.println("Please, only use: -a, -l, -i, -g, -c, -L, -D, -f, -r, quit");
						continue;
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
									System.out.println("Photo doesn't exist!");
									break;
								}

								if (photoID == null) {
									System.out.println("Incorrect photoID!");
									break;
								}

								Photo ph = new Photo(photoID);
								System.out.println(photoID);
								try {
									if (cm.addPhoto(currUser, photoID, ph, img)) {
										System.out.println("You've added given photo!");
									}
								} catch (DuplicatePhotoException e) {
									System.out.println(e.getMessage());
								}
							}
						} else {
							System.out.println("Please, call it like this: -a " + "<photos>");
							break;
						}
						break;
					case 'l':
						if (in.length == 2) {
							Iterable<PhotoData> list = cm.getAllPhotosData(currUser, in[1]);
							for (PhotoData pd : list) {
								System.out.println(pd.toString());
							}
						} else {
							System.out.println("Please, call it like this: -l " + "<userID>");
							break;
						}
						break;
					case 'i':
						if (in.length == 3) {
							PhotoOpinion opi = cm.getPhotoOpinion(currUser, in[1], in[2]);
							System.out.println(opi.toString());
						} else {
							System.out.println("Please, call it like this: -i " + "<userID> <photo>");
							break;
						}
						break;
					case 'g':
						if (in.length == 2) {
							Iterable<Photo> list = cm.getAllPhotos(currUser, in[1]);
							for (Photo pd : list) {
								System.out.println(pd.toString());
							}
							File savedPhotos = new File("SavedPhotos");
							savedPhotos.mkdir();
							for (Photo p : list) {
								File imgFile = new File(savedPhotos, p.getPhotoID() + '.' + p.getExtension());
								ImageIO.write(p.getImage(), p.getExtension(), imgFile);
							}
						} else {

							System.out.println("Please, call it like this: -g " + "<userID>");
							break;
						}
						break;
					case 'c':
						if (in.length == 4) {
							String[] comments = line.split("\"");
							if (cm.commentPhoto(currUser, comments[1], in[in.length - 2], in[in.length - 1]))
								System.out.println("You've commented given photo!");
						} else {
							System.out.println("Please, call it like this: -c " + "<comment> <userID> <photo>");
							break;
						}
						break;
					case 'L':
						if (in.length == 3) {
							if (cm.likePhoto(currUser, in[1], in[2]))
								System.out.println("You're now liking given photo!");
						} else {
							System.out.println("Please, call it like this: -L " + "<userID> <photo>");
							break;
						}
						break;
					case 'D':
						if (in.length == 3) {
							if (cm.dislikePhoto(currUser, in[1], in[2]))
								System.out.println("You're now disliking given photo!");
						} else {
							System.out.println("Please, call it like this: -D " + "<userID> <photo>");
							break;
						}
						break;
					case 'f':
						if (in.length > 1) {
							int i;
							for (i = 1; i < in.length; i++) {
								try {
									if (cm.followUser(currUser, in[i]))
										System.out.println("You're now following given username!");
								} catch (NoSuchUserException | AlreadyFollowingException e) {
									System.out.println(e.getMessage());
								}
							}
						} else {
							System.out.println("Please, call it like this: -f " + "<followUserIds>");
							break;
						}
						break;
					case 'r':
						if (in.length > 1) {
							int i;
							for (i = 1; i < in.length; i++) {
								try {
									if (cm.unfollowUser(currUser, in[i]))
										System.out.println("You're now unfollowing given username!");
								} catch (NoSuchUserException | AlreadyNotFollowingException e) {
									System.out.println(e.getMessage());
								}
							}
						} else {
							System.out.println("Please, call it like this: -r " + "<followUserIds>");
							break;
						}
						break;
					default:
						System.out.println("Please, only use: -a, -l, -i, -g, -c, -L, -D, -f, -r, quit");
						break;
					}
				}
			} catch (WrongUserPasswordException | NotFollowingException | NoSuchUserException e) {
				System.out.println("Something went wrong! " + e.getMessage());
			} catch (ClassNotFoundException e2) {
				e2.getStackTrace();
			} catch (NoSuchPhotoException | AlreadyFollowingException | DuplicatePhotoException | AlreadyLikedException
					| AlreadyDislikedException | AlreadyNotFollowingException e1) {
				System.out.println("That didn't work! " + e1.getMessage());
			}
		}
		scan.close();
	}
}