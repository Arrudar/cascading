/*
 * Copyright (c) 2007-2011 Concurrent, Inc. All Rights Reserved.
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

package cascading.flow.local;

import java.util.List;
import java.util.Properties;

import cascading.flow.FlowElement;
import cascading.flow.FlowProcess;
import cascading.flow.stream.Duct;
import cascading.flow.stream.Gate;
import cascading.flow.stream.SourceStage;
import cascading.flow.stream.StepStreamGraph;
import cascading.pipe.Group;
import cascading.tap.Tap;

/**
 *
 */
public class LocalStepStreamGraph extends StepStreamGraph
  {

  public LocalStepStreamGraph( FlowProcess<Properties> flowProcess, LocalFlowStep step )
    {
    super( flowProcess, step );

    buildGraph();
    setTraps();
    setScopes();

//    printGraph( "streamgraph.dot" );
    bind();
    }

  protected void buildGraph()
    {
    for( Object rhsElement : step.getSources() )
      {
      Duct rhsDuct = new SourceStage( flowProcess, (Tap) rhsElement );

      addHead( rhsDuct );

      handleDuct( (FlowElement) rhsElement, rhsDuct );
      }
    }

  protected Gate createCoGroupGate( Group element )
    {
    return new LocalCoGroupGate( flowProcess, (Group) element );
    }

  protected Gate createGroupByGate( Group element )
    {
    return new LocalGroupByGate( flowProcess, (Group) element );
    }

  protected boolean stopOnElement( FlowElement lhsElement, List<FlowElement> successors )
    {
    if( successors.isEmpty() )
      {
      if( !( lhsElement instanceof Tap ) )
        throw new IllegalStateException( "expected a Tap instance" );

      return true;
      }

    return false;
    }
  }