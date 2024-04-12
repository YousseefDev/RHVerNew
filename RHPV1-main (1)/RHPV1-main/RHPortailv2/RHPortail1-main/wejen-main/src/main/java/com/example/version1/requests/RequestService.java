    package com.example.version1.requests;

    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;
    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    @RequiredArgsConstructor
    public class RequestService {
        private final RequestRepository requestRepository;

        public List<Request> getAllRequest() {
            return requestRepository.findAll();
        }

        public List<Request> getRequestsByTypes(List<String> allowedTypes) {
            return requestRepository.findAll().stream()
                    .filter(request -> allowedTypes.contains(request.getTypeRequest()))
                    .collect(Collectors.toList());
        }



        public Request createRequest(Request request, Long userId) {  // Add userId as a method parameter
            request.setUserId(userId);
            return requestRepository.save(request);
        }

        public List<Request> getRequestsByUserId(Long userId) {
            return requestRepository.findByUserId(userId);
        }
        public Request getRequestById(Long id) {
            return requestRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Request not found."));
        }

        public Request updateRequest(Long id, Request updatedRequest) {
            Request request = getRequestById(id);
            request.setTitle(updatedRequest.getTitle());
            request.setDescription(updatedRequest.getDescription());
            request.setValid(updatedRequest.isValid());
            return requestRepository.save(request);
        }

        public void deleteRequest(Long id) {
            requestRepository.deleteById(id);
        }

        public Request updateRequestStatue(Long requestId, String newStatue) {
            Request request = getRequestById(requestId);
            request.setStatue(newStatue);
            return requestRepository.save(request);
        }


    }
