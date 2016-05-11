/*
 * Copyright (c) 2007-2016 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.cascading.org/
 *
 * This file is part of the Cascading project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cascading.flow.hadoop;

import cascading.property.ConfigDef;
import org.apache.hadoop.conf.Configuration;

/**
*
*/
public class ConfigurationSetter implements ConfigDef.Setter
  {
  private final Configuration conf;

  public ConfigurationSetter( Configuration conf )
    {
    this.conf = conf;
    }

  @Override
  public String set( String key, String value )
    {
    String oldValue = get( key );

    conf.set( key, value );

    return oldValue;
    }

  @Override
  public String update( String key, String value )
    {
    String oldValue = get( key );

    if( oldValue == null )
      conf.set( key, value );
    else if( !oldValue.contains( value ) )
      conf.set( key, oldValue + "," + value );

    return oldValue;
    }

  @Override
  public String get( String key )
    {
    String value = conf.get( key );

    if( value == null || value.isEmpty() )
      return null;

    return value;
    }
  }
