package ink.eason.starter.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import java.util.List;
import java.util.Objects;

@Component
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        this.serveDirectory(registry, "/", "classpath:/static/");
    }

    private void serveDirectory(ResourceHandlerRegistry registry, String endpoint, String location) {
        String[] endpointPatterns = endpoint.endsWith("/")
                ? new String[]{endpoint.substring(0, endpoint.length() - 1), endpoint, endpoint + "**"}
                : new String[]{endpoint, endpoint + "/", endpoint + "/**"};
        registry
                .addResourceHandler(endpointPatterns)
                .addResourceLocations(location.endsWith("/") ? location : location + "/")
                .resourceChain(false)
                .addResolver(new PathResourceResolver() {
                    @Override
                    public Resource resolveResource(HttpServletRequest request, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain) {
                        Resource resource = super.resolveResource(request, requestPath, locations, chain);
                        if (Objects.nonNull(resource)) {
                            return resource;
                        }
                        if (requestPath.startsWith("api/")) {
                            return null;
                        }
                        return super.resolveResource(request, "/index.html", locations, chain);
                    }
                });
    }
}
