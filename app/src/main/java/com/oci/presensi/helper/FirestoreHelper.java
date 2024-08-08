//package com.oci.presensi.helper;
//
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//import com.oci.presensi.model.ModelAbsensi;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class FirestoreHelper {
//
//    private FirebaseFirestore firestore;
//
//    public FirestoreHelper(FirebaseFirestore instance) {
//        firestore = FirebaseFirestore.getInstance();
//    }
//
//    public void addNewAbsensiFirestore(ModelAbsensi absensi, FirestoreCallback<Void> callback) {
//        String userId = String.valueOf(absensi.getId_user());
//        String documentId = String.valueOf(absensi.getId_absensi());
//        firestore.collection("absensi").document(userId)
//                .collection("absensiUser").document(documentId)
//                .set(absensi)
//                .addOnSuccessListener(aVoid -> callback.onSuccess(null))
//                .addOnFailureListener(callback::onFailure);
//    }
//
//    public void getAllAbsensiFirestore(FirestoreCallback<List<ModelAbsensi>> callback) {
//        firestore.collectionGroup("absensiUser")
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        List<ModelAbsensi> absensiList = new ArrayList<>();
//                        QuerySnapshot querySnapshot = task.getResult();
//                        for (QueryDocumentSnapshot document : querySnapshot) {
//                            ModelAbsensi absensi = document.toObject(ModelAbsensi.class);
//                            absensiList.add(absensi);
//                        }
//                        callback.onSuccess(absensiList);
//                    } else {
//                        callback.onFailure(task.getException());
//                    }
//                });
//    }
//
//    public void getLastIdAbsensi(FirestoreCallback<Integer> callback) {
//        firestore.collectionGroup("absensiUser")
//                .orderBy("id", com.google.firebase.firestore.Query.Direction.DESCENDING)
//                .limit(1)
//                .get()
//                .addOnSuccessListener(queryDocumentSnapshots -> {
//                    if (!queryDocumentSnapshots.isEmpty()) {
//                        ModelAbsensi lastAbsensi = queryDocumentSnapshots.toObjects(ModelAbsensi.class).get(0);
//                        callback.onSuccess(lastAbsensi.getId_absensi());
//                    } else {
//                        callback.onSuccess(0);
//                    }
//                })
//                .addOnFailureListener(callback::onFailure);
//    }
//
//    public void getAllAbsensiByIdUserFirestore(int userId, FirestoreCallback<List<ModelAbsensi>> callback) {
//        firestore.collection("absensi")
//                .document(String.valueOf(userId))
//                .collection("absensiUser")
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        List<ModelAbsensi> absensiList = new ArrayList<>();
//                        QuerySnapshot querySnapshot = task.getResult();
//                        for (QueryDocumentSnapshot document : querySnapshot) {
//                            ModelAbsensi absensi = document.toObject(ModelAbsensi.class);
//                            absensiList.add(absensi);
//                        }
//                        callback.onSuccess(absensiList);
//                    } else {
//                        callback.onFailure(task.getException());
//                    }
//                });
//    }
//}
