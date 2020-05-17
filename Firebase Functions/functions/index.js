const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);
// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
exports.createUser = functions.firestore
    .document('Users/{userId}')
    .onCreate((snap, context) => {
        const userID = snap.id;
        console.log("ID = " + userID);
        const db = admin.firestore();
        let infoRef = db.collection('InformationTemplates');
		let n = Math.floor(Math.random() * 5) % 2;
        let allInformation = infoRef.get()
            .then(snapshot => {
                snapshot.forEach(doc => {
					console.log("DATA: " + doc.data().Information);
                    array = doc.data().Information.split(",");
					console.log("DATA: " + array);
                    
					const indx = Math.floor(Math.random() * array.length);
                    const info = array[indx];
					
                    const title = doc.id;
                    const indx2 = Math.floor(Math.random() * 1);
                    let type = "Phone";
                    if (n % 2 === 1) {
                        type = "SocialMedia";
                    }
					n++;
                    console.log("Users/" + userID + " :: {" + title + ", " + info + ", " + type + "}");
                    db.collection('Users/' + userID + '/Information').add({
                        "Title": title,
                        "Information": info,
                        "By": type,
                    });
					return;

                });
                return "Done";
            })
            .catch(err => {
                console.log('Error getting documents', err);
            });
    });