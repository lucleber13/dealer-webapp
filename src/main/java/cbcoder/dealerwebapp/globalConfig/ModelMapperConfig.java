package cbcoder.dealerwebapp.globalConfig;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * ModelMapperConfig class provides the configuration for the ModelMapper bean.
 * It sets the matching strategy to STRICT, enables the skip null option, field matching, and field access level.
 * The bean is created and returned by the modelMapper() method.
 * The ModelMapper bean is used to map entities to DTOs and vice versa.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @see ModelMapper
 * @see Configuration
 * @since 2024-06-27
 */
@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }
}
