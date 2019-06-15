import * as functions from 'firebase-functions';
// import * as request from 'request';

const cors = require( 'cors' )( { origin: true } );

// // Start writing Firebase Functions
// // https://firebase.google.com/docs/functions/typescript
//
// export const helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

exports.search = functions.https.onRequest( ( req, res ) => {

  res.send( '[{"tfidf":"0.0052","url":"https://www.skysports.com/share/11740401"},{"tfidf":"0.0023","url":"https://www.skysports.com/share/11740370"},{"tfidf":"0.0021","url":"https://www.skysports.com/share/11740366"},{"tfidf":"0","url":"https://www.skysports.com/share/11740338"},{"tfidf":"0.0012","url":"https://www.skysports.com/share/11740227"},{"tfidf":"0.0038","url":"https://www.skysports.com/share/11740400"},{"tfidf":"0","url":"https://www.skysports.com/share/11728937"},{"tfidf":"0","url":"https://www.skysports.com/share/11731660"},{"tfidf":"0.0007","url":"https://www.skysports.com/share/11739856"},{"tfidf":"0.0035","url":"https://www.skysports.com/share/11740171"},{"tfidf":"0.0007","url":"https://www.skysports.com/share/11740333"},{"tfidf":"0.0009","url":"https://www.skysports.com/share/11709253"},{"tfidf":"0","url":"https://www.skysports.com/share/11709512"},{"tfidf":"0","url":"https://www.skysports.com/share/11713244"},{"tfidf":"0","url":"https://www.skysports.com/share/11740141"}]' );
  // request( 'http://142.4.212.26:8080/search?s=' + req.params[ 0 ], ( error, response, body ) => {
  //   res.send( body );
  // } );
} );


exports.cors = functions.https.onRequest( ( req, res ) => {
  cors( req, res, () => {
    res.send( 'Hello from Firebase!' );
  } );
} );
