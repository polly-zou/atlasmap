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
import { EnumValue, getAtlasmapLanguage } from '../impl/utils';
import { boolean, text } from '@storybook/addon-knobs';
import { ExpressionContent } from './ExpressionContent';
import React from 'react';
import { action } from '@storybook/addon-actions';

const obj = {
  title: 'UI|Mapping expression',
};
export default obj;

const executeFieldSearch = (): string[][] => {
  return [
    ['foo', 'Foo'],
    ['bar', 'Bar'],
    ['baz', 'Baz'],
  ];
};

const getFieldEnums = (): EnumValue[] => {
  return [
    { name: '[ None ]', ordinal: 0 },
    { name: 'rat', ordinal: 1 },
    { name: 'cat', ordinal: 2 },
    { name: 'bat', ordinal: 3 },
  ];
};

const setSelectedEnumValue = (): void => {};

export const disabled = () => (
  <ExpressionContent
    executeFieldSearch={executeFieldSearch}
    getFieldEnums={getFieldEnums}
    mappingExpressionAddField={action('mappingExpressionAddField')}
    isMappingExpressionEmpty={boolean('isMappingExpressionEmpty', true)}
    mappingExpressionInit={action('mappingExpressionInit')}
    mappingExpressionInsertText={action('mappingExpressionInsertText')}
    mappingExpressionRemoveField={action('mappingExpressionRemoveField')}
    mappingExpression={text('Mapping expression', '')}
    disabled={boolean('Disabled', true)}
    onToggle={action('onToggle')}
    setSelectedEnumValue={setSelectedEnumValue}
    getAtlasmapLanguage={getAtlasmapLanguage}
  />
);

export const enabledWithExpression = () => (
  <ExpressionContent
    executeFieldSearch={executeFieldSearch}
    getFieldEnums={getFieldEnums}
    mappingExpressionAddField={action('mappingExpressionAddField')}
    isMappingExpressionEmpty={boolean('isMappingExpressionEmpty', true)}
    mappingExpressionInit={action('mappingExpressionInit')}
    mappingExpressionInsertText={action('mappingExpressionInsertText')}
    mappingExpressionRemoveField={action('mappingExpressionRemoveField')}
    mappingExpression={text(
      'Mapping expression',
      'if (prop-city== Boston, city, state)',
    )}
    disabled={boolean('Disabled', false)}
    onToggle={action('onToggle')}
    setSelectedEnumValue={setSelectedEnumValue}
    getAtlasmapLanguage={getAtlasmapLanguage}
  />
);

export const enabledWithoutExpression = () => (
  <ExpressionContent
    executeFieldSearch={executeFieldSearch}
    getFieldEnums={getFieldEnums}
    mappingExpressionAddField={action('mappingExpressionAddField')}
    isMappingExpressionEmpty={boolean('isMappingExpressionEmpty', true)}
    mappingExpressionInit={action('mappingExpressionInit')}
    mappingExpressionInsertText={action('mappingExpressionInsertText')}
    mappingExpressionRemoveField={action('mappingExpressionRemoveField')}
    mappingExpression={text('Mapping expression', '')}
    disabled={boolean('Disabled', false)}
    onToggle={action('onToggle')}
    setSelectedEnumValue={setSelectedEnumValue}
    getAtlasmapLanguage={getAtlasmapLanguage}
  />
);
