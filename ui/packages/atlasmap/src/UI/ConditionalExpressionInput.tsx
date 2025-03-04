/*
    Copyright (C) 2017 Red Hat, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
import {
  ExpressionContent,
  IExpressionContentProps,
} from './ExpressionContent';
import React, { FunctionComponent } from 'react';
import { ToolbarGroup, ToolbarItem } from '@patternfly/react-core';
import { getFieldActionFunctions, getKeywords } from '../impl/utils';
import styles from './ConditionalExpressionInput.module.css';

export interface IConditionalExpressionInputProps
  extends IExpressionContentProps {}

export const ConditionalExpressionInput: FunctionComponent<
  IConditionalExpressionInputProps
> = ({
  executeFieldSearch,
  getFieldEnums,
  mappingExpressionAddField,
  isMappingExpressionEmpty,
  mappingExpressionInit,
  mappingExpressionInsertText,
  mappingExpressionRemoveField,
  mappingExpression,
  disabled,
  onToggle,
  setSelectedEnumValue,
  getAtlasmapLanguage,
}) => {
  return (
    <ToolbarGroup
      variant="filter-group"
      className={styles.toolbarItem}
      role={'form'}
    >
      <ToolbarItem className={styles.toolbarItem}>
        <ExpressionContent
          disabled={disabled}
          executeFieldSearch={executeFieldSearch}
          getFieldEnums={getFieldEnums}
          getFieldActionFunctions={getFieldActionFunctions}
          getKeywords={getKeywords}
          mappingExpressionAddField={mappingExpressionAddField}
          isMappingExpressionEmpty={isMappingExpressionEmpty}
          mappingExpressionInit={mappingExpressionInit}
          mappingExpressionInsertText={mappingExpressionInsertText}
          mappingExpressionRemoveField={mappingExpressionRemoveField}
          mappingExpression={mappingExpression}
          onToggle={onToggle}
          setSelectedEnumValue={setSelectedEnumValue}
          getAtlasmapLanguage={getAtlasmapLanguage}
        />
      </ToolbarItem>
    </ToolbarGroup>
  );
};
